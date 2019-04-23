package com.github.welshk.eirene.exoplayer

import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.welshk.eirene.R
import com.google.android.exoplayer2.ui.PlayerView
import okhttp3.OkHttpClient

abstract class EireneActivity : AppCompatActivity(), EireneContract.DispatchKeyEvent {
    private lateinit var presenter: EirenePresenter

    /**
     * Method for providing the Uri where the video is located.
     */
    abstract fun getUri(): Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getRootView(savedInstanceState)

        val playerView: PlayerView = findViewById(R.id.player_layout)
        val volumeView: View = findViewById(R.id.volume_layout)
        val volumeText: TextView = findViewById(R.id.volume_text)
        val volumeIcon: ImageView = findViewById(R.id.volume_icon)
        val progressBar: ProgressBar = findViewById(R.id.progress)

        presenter = EirenePresenter(
            baseContext,
            getOkHttpClient(),
            playerView,
            volumeView,
            volumeText,
            volumeIcon,
            progressBar,
            getUri(),
            isClosedCaptionEnabled()
        )

        presenter.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        if (::presenter.isInitialized) {
            presenter?.onDestroy()
        }

        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        if (::presenter.isInitialized) {
            presenter?.onStart()
        }
    }

    override fun onResume() {
        super.onResume()
        if (::presenter.isInitialized) {
            presenter?.onResume()
        }
    }

    override fun onPause() {
        if (::presenter.isInitialized) {
            presenter?.onPause()
        }
        super.onPause()
    }

    override fun onStop() {
        if (::presenter.isInitialized) {
            presenter?.onStop()
        }
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (::presenter.isInitialized) {
            presenter?.onSaveInstanceState(outState)
        }
        super.onSaveInstanceState(outState)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        return if (::presenter.isInitialized && event.keyCode != KeyEvent.KEYCODE_BACK) {
            presenter.dispatchKeyEvent(event)
        } else {
            super.dispatchKeyEvent(event)
        }
    }

    /**
     * Method for providing your OkHttpClient to the library.
     * This allows the user to configure caching, interceptors, and retrying logic.
     */
    open fun getOkHttpClient(): OkHttpClient? {
        return null
    }

    /**
     * Default to inflating the default layout eirene_activity.xml.
     * User can override this method and provide their own view for the player.
     * Layout should have views with IDs player_view, volume_layout, volume_text, volume_icon, progress
     */
    open fun getRootView(savedInstanceState: Bundle?) {
        setContentView(R.layout.eirene_activity)
    }

    open fun isClosedCaptionEnabled(): Boolean {
        return true
    }
}