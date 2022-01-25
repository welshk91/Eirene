package com.github.welshk.eirene.exoplayer

import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import android.widget.Toast
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.github.welshk.eirene.utils.DeviceUtil
import com.google.android.exoplayer2.PlaybackException


class EireneEventListener(
    private val playerView: PlayerView,
    private val progressBar: ProgressBar
) : Player.EventListener {

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (!playWhenReady) {
            //Show system UI when the video is paused
            DeviceUtil.showSystemUi(playerView.context)
        } else {
            DeviceUtil.hideSystemUi(playerView.context)
        }

        when (playbackState) {
            Player.STATE_IDLE ->
                // The player does not have any media to play yet.
                progressBar.visibility = View.VISIBLE
            Player.STATE_BUFFERING ->
                // The player is buffering (loading the content)
                progressBar.visibility = View.VISIBLE
            Player.STATE_READY ->
                // The player is able to immediately play
                progressBar.visibility = View.GONE
            Player.STATE_ENDED ->
                // The player has finished playing the media
                progressBar.visibility = View.GONE
        }
    }

    override fun onPlayerError(error: PlaybackException) {
        Log.e(javaClass.simpleName, "$error")
        Toast.makeText(playerView.context, error.message, Toast.LENGTH_LONG).show()
        super.onPlayerError(error)
    }
}