package com.github.welshk.eirene.exoplayer

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
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
    private val okHttpClient: OkHttpClient?,
    private val rootView: View,
    private val uri: Uri,
    private val isClosedCaptionEnabled: Boolean,
    private val isClosedCaptionToggleEnabled: Boolean
) : EireneContract.View, EireneContract.DispatchKeyEvent {
    private val userAgent: String = "mediaPlayerSample"

    private var player: SimpleExoPlayer? = null

    private val playerView: PlayerView = rootView.findViewById(R.id.player_layout)
    private val volumeView: View = rootView.findViewById(R.id.volume_layout)
    private val volumeText: TextView = rootView.findViewById(R.id.volume_text)
    private val volumeIcon: ImageView = rootView.findViewById(R.id.volume_icon)


    private val ccButton: ImageButton by lazy {
        rootView.findViewById<ImageButton>(R.id.exo_closed_caption_button)
    }


    private val progressBar: ProgressBar = rootView.findViewById(R.id.progress)

    private var trackSelector: DefaultTrackSelector? = null

    private var shouldAutoPlay = true
    private var playWhenReady: Boolean = false
    private var currentWindow: Int = 0
    private var playbackPosition: Long = 0

    private var volumeIncrements: Float = 0.toFloat()

    private val handler = Handler()
    private val fadeOutVolume: Runnable =
        Runnable { this.volumeView.animate().alpha(0f).duration = VOLUME_ANIMATE_FADE_OUT }

    private fun initializePlayer(context: Context) {
        playerView.requestFocus()
        progressBar.visibility = View.VISIBLE
        DeviceUtil.hideSystemUi(playerView.context)

        val bandwidthMeter = DefaultBandwidthMeter()

        val mediaDataSourceFactory: DataSource.Factory
        if (okHttpClient != null) {
            mediaDataSourceFactory = VideoUtil.buildHttpDataSourceFactory(
                okHttpClient,
                Util.getUserAgent(context, userAgent),
                bandwidthMeter
            )
        } else {
            mediaDataSourceFactory =
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
            trackSelector!!
        )
        playerView.player = player

        player!!.addListener(EireneEventListener(playerView, progressBar))
        player!!.playWhenReady = shouldAutoPlay


        val haveStartPosition = currentWindow != C.INDEX_UNSET
        if (haveStartPosition) {
            //player.seekTo(currentWindow, playbackPosition);
            //player.seekTo(currentWindow, player.getContentPosition());
            player!!.seekToDefaultPosition()
        }

        player!!.volume = ApplicationDataRepository.getVolume(context)
        volumeIncrements = ApplicationDataRepository.getVolumeIncrements(playerView.context)
        player!!.prepare(mediaSource, !haveStartPosition, false)
    }

    private fun releasePlayer() {
        progressBar.visibility = View.GONE
        handler.removeCallbacks(fadeOutVolume)
        DeviceUtil.showSystemUi(playerView.context)

        if (player != null) {
            presenter.saveLastKnownVolume(player!!.volume)
            updateStartPosition()
            shouldAutoPlay = player!!.playWhenReady
            player!!.release()
            player = null
            trackSelector = null
        }
    }

    private fun updateStartPosition() {
        playbackPosition = player!!.currentPosition
        currentWindow = player!!.currentWindowIndex
        playWhenReady = player!!.playWhenReady
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            playWhenReady = true
            currentWindow = 0
            playbackPosition = 0
        } else {
            playWhenReady = savedInstanceState.getBoolean(KEY_PLAY_WHEN_READY)
            currentWindow = savedInstanceState.getInt(KEY_WINDOW)
            playbackPosition = savedInstanceState.getLong(KEY_POSITION)
        }

    }

    override fun onStart(context: Context?) {
        if (Util.SDK_INT > 23) {
            if (context != null) {
                initializePlayer(context)
            }
        }
    }

    override fun onStop() {
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    override fun onPause() {
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    override fun onResume(context: Context?) {
        if (Util.SDK_INT <= 23 || player == null) {
            if (context != null) {
                initializePlayer(context)
            }
        }
    }

    override fun onDetach() {

    }

    override fun onAttach() {

    }

    override fun onSaveInstanceState(outState: Bundle) {
        updateStartPosition()

        outState.putBoolean(KEY_PLAY_WHEN_READY, playWhenReady)
        outState.putInt(KEY_WINDOW, currentWindow)
        outState.putLong(KEY_POSITION, playbackPosition)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        return when {
            event.keyCode == KeyEvent.KEYCODE_DPAD_UP -> {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    volumeIncrease()
                }
                true
            }
            event.keyCode == KeyEvent.KEYCODE_DPAD_DOWN -> {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    volumeDecrease()
                }
                true
            }
            else -> playerView.dispatchKeyEvent(event)
        }
    }

    private fun volumeIncrease() {
        player!!.volume =
            if (player!!.volume + volumeIncrements > 1f) 1f else player!!.volume + volumeIncrements
        volumeIcon.setImageResource(R.drawable.volume_up)
        volumeChanged()
    }

    private fun volumeDecrease() {
        player!!.volume =
            if (player!!.volume - volumeIncrements < 0f) 0f else player!!.volume - volumeIncrements

        if (FormattingUtil.volumeFormatted(player!!.volume) == 0) {
            volumeIcon.setImageResource(R.drawable.volume_off)
        } else {
            volumeIcon.setImageResource(R.drawable.volume_down)
        }
        volumeChanged()
    }

    private fun volumeChanged() {
        volumeText.text = (FormattingUtil.volumeFormatted(player!!.volume).toString())
        volumeView.animate().alpha(1f).duration = VOLUME_ANIMATE_FADE_IN
        handler.removeCallbacks(fadeOutVolume)
        handler.postDelayed(fadeOutVolume, VOLUME_ANIMATE_FADE_OUT_DELAY)
    }

    private fun toggleCaptions() {
        if (trackSelector!!.parameters.getRendererDisabled(C.TRACK_TYPE_VIDEO)) {
            enableCaptions()
        } else {
            disableCaptions()
        }
    }

    private fun disableCaptions() {
        trackSelector!!.parameters = DefaultTrackSelector.ParametersBuilder()
            .setRendererDisabled(C.TRACK_TYPE_VIDEO, true)
            .build()

        ccButton.alpha = .6f
    }

    private fun enableCaptions() {
        trackSelector!!.parameters = DefaultTrackSelector.ParametersBuilder()
            .setRendererDisabled(C.TRACK_TYPE_VIDEO, false)
            .build()

        ccButton.alpha = 1f
    }

    companion object {
        const val KEY_PLAY_WHEN_READY = "play_when_ready"
        const val KEY_WINDOW = "window"
        const val KEY_POSITION = "position"

        const val VOLUME_ANIMATE_FADE_IN: Long = 800
        const val VOLUME_ANIMATE_FADE_OUT: Long = 800
        const val VOLUME_ANIMATE_FADE_OUT_DELAY: Long = 2000
    }
}