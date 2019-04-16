package com.github.welshk.eirene.exoplayer

import android.os.Bundle
import android.content.Context
import android.view.KeyEvent

class ExoPlayerContract {
    interface View {
        fun onCreate(savedInstanceState: Bundle)

        fun onStart(context: Context)

        fun onStop()

        fun onPause()

        fun onResume(context: Context)

        fun onDetach()

        fun onAttach()

        fun onSaveInstanceState(outState: Bundle)
    }

    interface Presenter {
        fun onCreate(savedInstanceState: Bundle)

        fun onDestroy()

        fun onStart()

        fun onStop()

        fun onPause()

        fun onResume()

        fun onDetach()

        fun onAttach()

        fun onSaveInstanceState(outState: Bundle)

        fun saveLastKnownVolume(volume: Float)
    }

    interface DispatchKeyEvent {
        fun dispatchKeyEvent(event: KeyEvent): Boolean
    }
}
