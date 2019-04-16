package com.github.welshk.eirene.demo

import android.content.Context
import androidx.fragment.app.Fragment
import com.github.welshk.eirene.exoplayer.ExoPlayerContract
import android.os.Bundle
import android.view.KeyEvent
import com.github.welshk.eirene.exoplayer.ExoPlayerPresenter
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.LinearLayout
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.annotation.Nullable
import com.github.welshk.eirene.demo.networking.NetworkManager
import com.google.android.exoplayer2.ui.PlayerView

class ExoPlayerFragment : Fragment(), ExoPlayerContract.DispatchKeyEvent {
    private lateinit var presenter: ExoPlayerPresenter

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val videoView = inflater.inflate(R.layout.exo_player_fragment, container, false)

        if (arguments != null && arguments!!.getString(Constants.INTENT_EXTRA_URL) != null) {
            val url = arguments!!.getString(Constants.INTENT_EXTRA_URL)
//            val playerView: PlayerView = videoView.findViewById(R.id.player_view)
//            val volumeView: LinearLayout = videoView.findViewById(R.id.volume_layout)
//            val volumeText: TextView = videoView.findViewById(R.id.volume_text)
//            val volumeIcon: ImageView = videoView.findViewById(R.id.volume_icon)
//            val progressBar: ProgressBar = videoView.findViewById(R.id.progress)
//
//            presenter = ExoPlayerPresenter(
//                context,
//                NetworkManager.Instance.getHttpClient()!!.build(),
//                playerView,
//                volumeView,
//                volumeText,
//                volumeIcon,
//                progressBar,
//                url
//            )
//            presenter.onCreate(savedInstanceState)
        }

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
}