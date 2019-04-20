package com.github.welshk.eirene.exoplayer

import com.github.welshk.eirene.R
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ui.PlayerView
import okhttp3.OkHttpClient

abstract class EireneActivity : AppCompatActivity(), EireneContract.DispatchKeyEvent {
    private lateinit var presenter: EirenePresenter

    abstract fun getUrl(): String
    abstract fun getOkHttpClient(): OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPlayerView(savedInstanceState)

        val playerView: PlayerView = findViewById(R.id.player_view)
        val volumeView: LinearLayout = findViewById(R.id.volume_layout)
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
            getUrl()
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
     * Default to inflating the default layout exo_player_view
     * User can override this method and provide their own view for the player
     */
    open fun getPlayerView(savedInstanceState: Bundle?) {
        setContentView(R.layout.eirene_layout)
    }
}