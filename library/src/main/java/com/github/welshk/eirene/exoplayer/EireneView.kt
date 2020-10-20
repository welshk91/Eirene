package com.github.welshk.eirene.exoplayer

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.github.welshk.eirene.R
import com.github.welshk.eirene.data.ApplicationDataRepository
import com.github.welshk.eirene.utils.DeviceUtil
import com.github.welshk.eirene.utils.FormattingUtil
import com.github.welshk.eirene.utils.VideoUtil
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.util.Util
import okhttp3.OkHttpClient


class EireneView(
    private val presenter: EirenePresenter,
    private val context: Context,
    private val okHttpClient: OkHttpClient?,
    private val rootView: View,
    private val uri: Uri,
    private val isClosedCaptionEnabled: Boolean,
    private val isClosedCaptionToggleEnabled: Boolean
) : EireneContract.DispatchKeyEvent, LifecycleObserver {
    private val userAgent: String = "mediaPlayerSample"

    private var player: SimpleExoPlayer? = null

    private val playerView: PlayerView = rootView.findViewById(R.id.player_layout)
    private val volumeView: View = rootView.findViewById(R.id.volume_layout)
    private val volumeText: TextView = rootView.findViewById(R.id.volume_text)
    private val volumeIcon: ImageView = rootView.findViewById(R.id.volume_icon)


    private val ccButton: ImageButton by lazy {
        rootView.findViewById(R.id.exo_closed_caption_button)
    }


    private val progressBar: ProgressBar = rootView.findViewById(R.id.progress)

    private lateinit var trackSelector: DefaultTrackSelector

    private var shouldAutoPlay = true
    private var playWhenReady: Boolean = false
    private var currentWindow: Int = 0
    private var playbackPosition: Long = 0

    private var volumeIncrements: Float = 0.toFloat()

    private val handler = Handler()
    private val fadeOutVolume: Runnable =
        Runnable { this.volumeView.animate().alpha(0f).duration = VOLUME_ANIMATE_FADE_OUT }

    private fun initializePlayer() {
        playerView.requestFocus()
        progressBar.visibility = View.VISIBLE
        DeviceUtil.hideSystemUi(playerView.context)

        val bandwidthMeter = DefaultBandwidthMeter()

        val mediaDataSourceFactory: DataSource.Factory
        mediaDataSourceFactory = if (okHttpClient != null) {
            VideoUtil.buildHttpDataSourceFactory(
                okHttpClient,
                Util.getUserAgent(context, userAgent),
                bandwidthMeter
            )
        } else {
            VideoUtil.buildDefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, userAgent)
            )
        }

        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory()
        trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)

        if (isClosedCaptionToggleEnabled) {
            ccButton.visibility = View.VISIBLE
            ccButton.setOnClickListener {
                toggleCaptions()
            }
        }

        if (isClosedCaptionEnabled) {
            enableCaptions()
        } else {
            disableCaptions()
        }

        val mediaSource = VideoUtil.getMediaSource(mediaDataSourceFactory, uri)

        player = ExoPlayerFactory.newSimpleInstance(
            context,
            VideoUtil.getRenderersFactory(context),
            trackSelector
        )
        playerView.player = player

        player?.let {
            it.addListener(EireneEventListener(playerView, progressBar))
            it.playWhenReady = shouldAutoPlay

            val haveStartPosition = currentWindow != C.INDEX_UNSET
            if (haveStartPosition && !it.isCurrentWindowDynamic) {
                it.seekTo(currentWindow, playbackPosition)
            } else {
                it.seekToDefaultPosition()
            }

            it.volume = ApplicationDataRepository.getVolume(context)
            volumeIncrements = ApplicationDataRepository.getVolumeIncrements(playerView.context)
            it.prepare(mediaSource, !haveStartPosition, false)
        }
    }

    private fun releasePlayer() {
        progressBar.visibility = View.GONE
        handler.removeCallbacks(fadeOutVolume)
        DeviceUtil.showSystemUi(playerView.context)

        player?.let {
            presenter.saveLastKnownVolume(it.volume)
            presenter.saveLastKnownPosition(it.currentPosition)
            presenter.saveLastKnownCurrentWindow(it.currentWindowIndex)
            presenter.saveLastKnownPlayWhenReady(it.playWhenReady)
            shouldAutoPlay = it.playWhenReady
            it.release()
            player = null
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        playWhenReady = presenter.loadLastKnownPlayWhenReady()
        currentWindow = presenter.loadLastKnownCurrentWindow()
        playbackPosition = presenter.loadLastKnownPosition()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer()
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        return when (event.keyCode) {
            KeyEvent.KEYCODE_DPAD_UP -> {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    volumeIncrease()
                }
                true
            }
            KeyEvent.KEYCODE_DPAD_DOWN -> {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    volumeDecrease()
                }
                true
            }
            else -> playerView.dispatchKeyEvent(event)
        }
    }

    private fun volumeIncrease() {
        player?.let {
            it.volume =
                if (it.volume + volumeIncrements > 1f) 1f else it.volume + volumeIncrements
            volumeIcon.setImageResource(R.drawable.volume_up)
            volumeChanged()
        }
    }

    private fun volumeDecrease() {
        player?.let {
            it.volume =
                if (it.volume - volumeIncrements < 0f) 0f else it.volume - volumeIncrements

            if (FormattingUtil.volumeFormatted(it.volume) == 0) {
                volumeIcon.setImageResource(R.drawable.volume_off)
            } else {
                volumeIcon.setImageResource(R.drawable.volume_down)
            }
            volumeChanged()
        }
    }

    private fun volumeChanged() {
        player?.let {
            volumeText.text = (FormattingUtil.volumeFormatted(it.volume).toString())
            volumeView.animate().alpha(1f).duration = VOLUME_ANIMATE_FADE_IN
            handler.removeCallbacks(fadeOutVolume)
            handler.postDelayed(fadeOutVolume, VOLUME_ANIMATE_FADE_OUT_DELAY)
        }
    }

    private fun toggleCaptions() {
        if (trackSelector.parameters.getRendererDisabled(C.TRACK_TYPE_VIDEO)) {
            enableCaptions()
        } else {
            disableCaptions()
        }
    }

    private fun disableCaptions() {
        trackSelector.parameters = DefaultTrackSelector.ParametersBuilder()
            .setRendererDisabled(C.TRACK_TYPE_VIDEO, true)
            .build()

        ccButton.alpha = .6f
    }

    private fun enableCaptions() {
        trackSelector.parameters = DefaultTrackSelector.ParametersBuilder()
            .setRendererDisabled(C.TRACK_TYPE_VIDEO, false)
            .build()

        ccButton.alpha = 1f
    }

    companion object {
        const val VOLUME_ANIMATE_FADE_IN: Long = 800
        const val VOLUME_ANIMATE_FADE_OUT: Long = 800
        const val VOLUME_ANIMATE_FADE_OUT_DELAY: Long = 2000
    }
}