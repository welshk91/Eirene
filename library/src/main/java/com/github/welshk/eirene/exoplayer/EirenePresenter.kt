package com.github.welshk.eirene.exoplayer

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.github.welshk.eirene.data.ApplicationDataRepository
import com.github.welshk.eirene.data.SharedPreferenceManager
import okhttp3.OkHttpClient

class EirenePresenter(
    private val context: Context?,
    private val lifecycle: Lifecycle,
    okHttpClient: OkHttpClient?,
    rootView: View,
    uri: Uri,
    isClosedCaptionEnabled: Boolean,
    isClosedCaptionToggleEnabled: Boolean
) : EireneContract.Presenter, EireneContract.DispatchKeyEvent, LifecycleObserver {
    private var view: EireneView =
        EireneView(
            this,
            context!!,
            okHttpClient,
            rootView,
            uri,
            isClosedCaptionEnabled,
            isClosedCaptionToggleEnabled
        )

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        lifecycle.addObserver(view)
    }

    override fun onSaveInstanceState(outState: Bundle) {

    }

    override fun saveLastKnownVolume(volume: Float) {
        context?.let {
            ApplicationDataRepository.setVolume(context, volume)
        }
    }

    override fun saveLastKnownPosition(position: Long) {
        context?.let {
            ApplicationDataRepository.setLastKnownPosition(it, position)
        }
    }

    override fun saveLastKnownPlayWhenReady(playWhenReady: Boolean) {
        context?.let {
            ApplicationDataRepository.setLastKnownPlayWhenReady(it, playWhenReady)
        }
    }

    override fun saveLastKnownCurrentWindow(currentWindow: Int) {
        context?.let {
            ApplicationDataRepository.setLastKnownCurrentWindow(it, currentWindow)
        }
    }

    override fun loadLastKnownPosition(): Long {
        return if (context != null) {
            ApplicationDataRepository.getLastKnownPosition(context)
        } else {
            SharedPreferenceManager.DEFAULT_VALUE_POSITION
        }
    }

    override fun loadLastKnownPlayWhenReady(): Boolean {
        return if (context != null) {
            ApplicationDataRepository.getLastKnownPlayWhenReady(context)
        } else {
            SharedPreferenceManager.DEFAULT_VALUE_PLAY_WHEN_READY
        }
    }

    override fun loadLastKnownCurrentWindow(): Int {
        return if (context != null) {
            ApplicationDataRepository.getLastKnownCurrentWindow(context)
        } else {
            SharedPreferenceManager.DEFAULT_VALUE_CURRENT_WINDOW
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        return view.dispatchKeyEvent(event)
    }
}