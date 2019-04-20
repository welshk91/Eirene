package com.github.welshk.eirene.exoplayer

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView

import com.github.welshk.eirene.R
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.util.Util
import com.github.welshk.eirene.data.ApplicationDataRepository
import com.github.welshk.eirene.utils.DeviceUtil
import com.github.welshk.eirene.utils.FormattingUtil
import com.github.welshk.eirene.utils.VideoUtil
import okhttp3.OkHttpClient


class EireneView(
    okHttpClient: OkHttpClient,
    playerView: PlayerView,
    volumeView: LinearLayout,
    volumeText: TextView,
    volumeIcon: ImageView,
    progressBar: ProgressBar,
    eirenePresenter: EirenePresenter,
    url: String
) : EireneContract.View, EireneContract.DispatchKeyEvent {
    private val presenter: EirenePresenter = eirenePresenter

    private val okHttpClient: OkHttpClient = okHttpClient
    private val playerView: PlayerView = playerView
    private var player: SimpleExoPlayer? = null
    private val volumeView: LinearLayout = volumeView
    private val volumeText: TextView = volumeText
    private val volumeIcon: ImageView = volumeIcon
    private val progressBar: ProgressBar = progressBar
    private val url: String = url

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

        val mediaDataSourceFactory = VideoUtil.buildHttpDataSourceFactory(
            okHttpClient,
            Util.getUserAgent(context, "mediaPlayerSample"),
            bandwidthMeter
        )

        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        disableCaptions()

        val mediaSource = VideoUtil.getMediaSource(mediaDataSourceFactory, url)

        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector!!)
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
        player!!.volume = if (player!!.volume + volumeIncrements > 1f) 1f else player!!.volume + volumeIncrements
        volumeIcon.setImageResource(R.drawable.volume_up)
        volumeChanged()
    }

    private fun volumeDecrease() {
        player!!.volume = if (player!!.volume - volumeIncrements < 0f) 0f else player!!.volume - volumeIncrements

        if (player!!.volume == 0f) {
            volumeIcon.setImageResource(R.drawable.volume_off)
        } else {
            volumeIcon.setImageResource(R.drawable.volume_down)
        }
        volumeChanged()
    }

    private fun volumeChanged() {
        volumeText.text = (FormattingUtil.volumeFormatted(player!!.volume))
        volumeView.animate().alpha(1f).duration = VOLUME_ANIMATE_FADE_IN
        handler.removeCallbacks(fadeOutVolume)
        handler.postDelayed(fadeOutVolume, VOLUME_ANIMATE_FADE_OUT_DELAY)
    }

    private fun disableCaptions() {
        trackSelector!!.parameters = DefaultTrackSelector.ParametersBuilder()
            .setRendererDisabled(C.TRACK_TYPE_VIDEO, true)
            .build()
    }

    private fun enableCaptions() {
        trackSelector!!.parameters = DefaultTrackSelector.ParametersBuilder()
            .setRendererDisabled(C.TRACK_TYPE_VIDEO, false)
            .build()
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