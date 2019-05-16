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
        view?.onSaveInstanceState(outState)
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