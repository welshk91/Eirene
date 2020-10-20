package com.github.welshk.eirene.exoplayer

import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.github.welshk.eirene.R
import com.github.welshk.eirene.data.ApplicationDataRepository
import okhttp3.OkHttpClient

abstract class EireneFragment : Fragment(), EireneContract.DispatchKeyEvent {
    private lateinit var presenter: EirenePresenter

    /**
     * Method for providing the Uri where the video is located.
     */
    abstract fun getUri(): Uri

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        val videoView = getRootView(inflater, container)

        if (savedInstanceState == null) {
            context?.let {
                ApplicationDataRepository.setPlayerDefaults(it)
            }
        }

        context?.let {
            presenter = EirenePresenter(
                it,
                lifecycle,
                getOkHttpClient(),
                videoView,
                getUri(),
                isClosedCaptionEnabled(),
                isClosedCaptionToggleEnabled()
            )
        }

        lifecycle.addObserver(presenter)
        return videoView
    }

    /**
     * This allows us to to not reset to the video player defaults (location) when a user rotates the screen.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        //TODO: This is ugly. This is just to make sure the savedInstanceBundle isn't null
        outState.putBoolean("make_sure_bundle_isnt_empty_key", false)
        super.onSaveInstanceState(outState)
    }

    /**
     * Passes all KeyCodes (except Back) to the presenter to be handle by the video player.
     * This allows us to handle when a user clicks up/down on the Android TV remote, adjusting the volume.
     */
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
    open fun getRootView(inflater: LayoutInflater, @Nullable container: ViewGroup?): View {
        return inflater.inflate(R.layout.eirene_fragment, container, false)
    }

    /**
     * Default to Closed Caption being enabled
     */
    open fun isClosedCaptionEnabled(): Boolean {
        return true
    }

    /**
     * Default to Closed Caption icon being visible to the user
     */
    open fun isClosedCaptionToggleEnabled(): Boolean {
        return isClosedCaptionEnabled()
    }
}