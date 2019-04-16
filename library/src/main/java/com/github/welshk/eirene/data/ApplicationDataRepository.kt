package com.github.welshk.eirene.data

import android.content.Context

class ApplicationDataRepository {
    companion object {

        @JvmStatic
        fun setVolume(context: Context, volume: Float) {
            SharedPreferenceManager.Instance.setVolume(context, volume)
        }

        @JvmStatic
        fun getVolume(context: Context): Float {
            return SharedPreferenceManager.Instance.getVolume(context)
        }

        @JvmStatic
        fun getVolumeIncrements(context: Context): Float {
            return SharedPreferenceManager.Instance.getVolumeIncrements(context)
        }

        @JvmStatic
        fun setVolumeIncrements(context: Context, volumeIncrements: Float) {
            SharedPreferenceManager.Instance.setVolumeIncrements(context, volumeIncrements)
        }
    }
}