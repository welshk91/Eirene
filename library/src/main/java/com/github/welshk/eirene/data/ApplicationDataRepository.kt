package com.github.welshk.eirene.data

import android.content.Context

object ApplicationDataRepository {

    @JvmStatic
    fun setVolume(context: Context, volume: Float) {
        val preferenceManager = SharedPreferenceManager(context)
        preferenceManager.volume = volume
    }

    @JvmStatic
    fun getVolume(context: Context): Float {
        val preferenceManager = SharedPreferenceManager(context)
        return preferenceManager.volume
    }

    @JvmStatic
    fun getVolumeIncrements(context: Context): Float {
        val preferenceManager = SharedPreferenceManager(context)
        return preferenceManager.volumeIncrements
    }

    @JvmStatic
    fun setVolumeIncrements(context: Context, volumeIncrements: Float) {
        val preferenceManager = SharedPreferenceManager(context)
        preferenceManager.volumeIncrements = volumeIncrements
    }

    @JvmStatic
    fun setLastKnownPosition(context: Context, position: Long) {
        val preferenceManager = SharedPreferenceManager(context)
        preferenceManager.lastKnownPosition = position
    }

    @JvmStatic
    fun getLastKnownPosition(context: Context): Long {
        val preferenceManager = SharedPreferenceManager(context)
        return preferenceManager.lastKnownPosition
    }

    @JvmStatic
    fun setLastKnownPlayWhenReady(context: Context, playWhenReady: Boolean) {
        val preferenceManager = SharedPreferenceManager(context)
        preferenceManager.lastKnownPlayWhenReady = playWhenReady
    }

    @JvmStatic
    fun getLastKnownPlayWhenReady(context: Context): Boolean {
        val preferenceManager = SharedPreferenceManager(context)
        return preferenceManager.lastKnownPlayWhenReady
    }

    @JvmStatic
    fun setLastKnownCurrentWindow(context: Context, currentWindow: Int) {
        val preferenceManager = SharedPreferenceManager(context)
        preferenceManager.lastKnownCurrentWindow = currentWindow
    }

    @JvmStatic
    fun getLastKnownCurrentWindow(context: Context): Int {
        val preferenceManager = SharedPreferenceManager(context)
        return preferenceManager.lastKnownCurrentWindow
    }

    @JvmStatic
    fun setPlayerDefaults(context: Context) {
        val preferenceManager = SharedPreferenceManager(context)
        preferenceManager.lastKnownPosition = SharedPreferenceManager.DEFAULT_VALUE_POSITION
        preferenceManager.lastKnownPlayWhenReady = SharedPreferenceManager.DEFAULT_VALUE_PLAY_WHEN_READY
        preferenceManager.lastKnownCurrentWindow = SharedPreferenceManager.DEFAULT_VALUE_CURRENT_WINDOW
    }
}