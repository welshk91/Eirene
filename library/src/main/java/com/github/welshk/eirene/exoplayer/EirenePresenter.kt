package com.github.welshk.eirene.exoplayer

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.github.welshk.eirene.data.ApplicationDataRepository

import com.google.android.exoplayer2.ui.PlayerView
import okhttp3.OkHttpClient

class EirenePresenter(
    private val context: Context?,
    okHttpClient: OkHttpClient,
    playerView: PlayerView,
    volumeView: LinearLayout,
    volumeText: TextView,
    volumeIcon: ImageView,
    progressBar: ProgressBar,
    url: String
) : EireneContract.Presenter, EireneContract.DispatchKeyEvent {
    private var view: EireneView? =
        EireneView(okHttpClient, playerView, volumeView, volumeText, volumeIcon, progressBar, this, url)

    override fun onCreate(savedInstanceState: Bundle?) {
        if (view != null) {
            view!!.onCreate(savedInstanceState)
        }
    }

    override fun onDestroy() {
        view = null
    }

    override fun onStart() {
        view?.onStart(context)
    }

    override fun onStop() {
        view?.onStop()
    }

    override fun onPause() {
        view?.onPause()
    }

    override fun onResume() {
        view?.onResume(context)
    }

    override fun onDetach() {
        view!!.onDetach()
    }

    override fun onAttach() {
        view!!.onAttach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        view!!.onSaveInstanceState(outState)
    }

    override fun saveLastKnownVolume(volume: Float) {
        if (context != null) {
            ApplicationDataRepository.setVolume(context, volume)
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        return if (view != null) {
            view!!.dispatchKeyEvent(event)
        } else {
            false
        }
    }
}