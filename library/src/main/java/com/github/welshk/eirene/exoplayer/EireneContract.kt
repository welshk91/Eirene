package com.github.welshk.eirene.exoplayer

import android.os.Bundle
import android.view.KeyEvent

class EireneContract {
    interface View {

    }

    interface Presenter {
        fun onSaveInstanceState(outState: Bundle)

        fun saveLastKnownVolume(volume: Float)

        fun saveLastKnownPosition(position: Long)

        fun saveLastKnownPlayWhenReady(playWhenReady: Boolean)

        fun saveLastKnownCurrentWindow(currentWindow: Int)

        fun loadLastKnownPosition(): Long

        fun loadLastKnownPlayWhenReady(): Boolean

        fun loadLastKnownCurrentWindow(): Int
    }

    interface DispatchKeyEvent {
        fun dispatchKeyEvent(event: KeyEvent): Boolean
    }
}
