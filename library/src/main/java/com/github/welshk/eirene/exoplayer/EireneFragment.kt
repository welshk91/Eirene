package com.github.welshk.eirene.exoplayer

import com.github.welshk.eirene.R
import android.content.Context
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

    abstract fun getUrl(): String
    abstract fun getOkHttpClient(): OkHttpClient

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val videoView = getPlayerView(inflater, container)

        val playerView: PlayerView = videoView.findViewById(R.id.player_view)
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
            getUrl()
        )
        presenter.onCreate(savedInstanceState)

        return videoView
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        presenter?.onStart()
    }

    override fun onResume() {
        super.onResume()
        presenter?.onResume()
    }

    override fun onPause() {
        presenter?.onPause()
        super.onPause()
    }

    override fun onStop() {
        presenter?.onStop()
        super.onStop()
    }

    override fun onDetach() {
        presenter?.onDetach()
        super.onDetach()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter?.onAttach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        presenter?.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        return if (presenter != null) {
            presenter.dispatchKeyEvent(event)
        } else {
            false
        }
    }

    /**
     * Default to inflating the default layout exo_player_view
     * User can override this method and provide their own view for the player
     */
    open fun getPlayerView(inflater: LayoutInflater, @Nullable container: ViewGroup?): View {
        return inflater.inflate(R.layout.eirene_layout, container, false)
    }
}