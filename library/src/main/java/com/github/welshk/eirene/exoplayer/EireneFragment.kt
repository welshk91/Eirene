package com.github.welshk.eirene.exoplayer

import com.github.welshk.eirene.R
import android.content.Context
import android.net.Uri
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.LinearLayout
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.annotation.Nullable
import com.google.android.exoplayer2.ui.PlayerView
import okhttp3.OkHttpClient

abstract class EireneFragment : Fragment(), EireneContract.DispatchKeyEvent {
    private lateinit var presenter: EirenePresenter

    /**
     * Method for providing the Uri where the video is located.
     */
    abstract fun getUri(): Uri

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val videoView = getPlayerView(inflater, container)

        val playerView: PlayerView = videoView.findViewById(R.id.player_layout)
        val volumeView: LinearLayout = videoView.findViewById(R.id.volume_layout)
        val volumeText: TextView = videoView.findViewById(R.id.volume_text)
        val volumeIcon: ImageView = videoView.findViewById(R.id.volume_icon)
        val progressBar: ProgressBar = videoView.findViewById(R.id.progress)

        presenter = EirenePresenter(
            context,
            getOkHttpClient(),
            playerView,
            volumeView,
            volumeText,
            volumeIcon,
            progressBar,
            getUri()
        )
        presenter.onCreate(savedInstanceState)

        return videoView
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

    override fun onDetach() {
        if (::presenter.isInitialized) {
            presenter?.onDetach()
        }
        super.onDetach()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (::presenter.isInitialized) {
            presenter?.onAttach()
        }
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
            false
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
     * Default to inflating the default layout eirene_fragment.xml.
     * User can override this method and provide their own view for the player.
     * Layout should have views with IDs player_view, volume_layout, volume_text, volume_icon, progress
     */
    open fun getPlayerView(inflater: LayoutInflater, @Nullable container: ViewGroup?): View {
        return inflater.inflate(R.layout.eirene_fragment, container, false)
    }
}