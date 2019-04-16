package com.github.welshk.eirene.data

import android.content.Context

object ApplicationDataRepository {

    @JvmStatic
    fun setVolume(context: Context, volume: Float) {
        val preferenceManager = SharedPreferenceManager(context)
        preferenceManager?.volume = volume
    }

    @JvmStatic
    fun getVolume(context: Context): Float {
        val preferenceManager = SharedPreferenceManager(context)
        return preferenceManager!!.volume
    }

    @JvmStatic
    fun getVolumeIncrements(context: Context): Float {
        val preferenceManager = SharedPreferenceManager(context)
        return preferenceManager!!.volumeIncrements
    }

    @JvmStatic
    fun setVolumeIncrements(context: Context, volumeIncrements: Float) {
        val preferenceManager = SharedPreferenceManager(context)
        preferenceManager?.volumeIncrements = volumeIncrements
    }
}