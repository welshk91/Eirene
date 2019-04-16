package com.github.welshk.eirene.demo

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.KeyEvent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.welshk.eirene.demo.networking.NetworkManager
import com.github.welshk.eirene.exoplayer.ExoPlayerContract
import com.github.welshk.eirene.exoplayer.ExoPlayerPresenter
import com.google.android.exoplayer2.ui.PlayerView

class ExoPlayerActivity : AppCompatActivity(), ExoPlayerContract.DispatchKeyEvent {
    private lateinit var presenter: ExoPlayerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exo_player_fragment)

        if (intent?.extras?.getString(Constants.INTENT_EXTRA_URL) != null) {
            val url = intent?.extras?.getString(Constants.INTENT_EXTRA_URL)
            val playerView: PlayerView = findViewById(R.id.player_view)
            val volumeView: LinearLayout = findViewById(R.id.volume_layout)
            val volumeText: TextView = findViewById(R.id.volume_text)
            val volumeIcon: ImageView = findViewById(R.id.volume_icon)
            val progressBar: ProgressBar = findViewById(R.id.progress)

            presenter = ExoPlayerPresenter(
                baseContext,
                NetworkManager.Instance.getHttpClient()!!.build(),
                playerView,
                volumeView,
                volumeText,
                volumeIcon,
                progressBar,
                url!!
            )

            presenter.onCreate(savedInstanceState)
        }
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
}