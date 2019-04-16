package com.github.welshk.eirene.data

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager


class SharedPreferenceManager(context: Context) {
    val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    val VOLUME_KEY = "player_volume"
    val VOLUME_INCREMENTS_KEY = "player_volume_increments"

    private fun getEditor(): SharedPreferences.Editor {
        return prefs.edit()
    }

    var volume: Float
        get() = prefs.getFloat(VOLUME_KEY, DEFAULT_VALUE_PLAYER_VOLUME)
        set(value) = getEditor().putFloat(VOLUME_KEY, value).apply()

    var volumeIncrements: Float
        get() = getVolumeIncrementsBlah()
        set(value) = getEditor().putFloat(VOLUME_INCREMENTS_KEY, value).apply()

    fun getVolumeIncrementsBlah(): Float {
        var volumePreference: Int

        try {
            volumePreference = Integer.parseInt(
                prefs.getString(VOLUME_INCREMENTS_KEY, DEFAULT_VALUE_VOLUME_INCREMENTS)
            )
            return volumePreference / 100f
        } catch (exception: NumberFormatException) {
            volumePreference = Integer.parseInt(DEFAULT_VALUE_VOLUME_INCREMENTS)
            volumeIncrements = (volumePreference / 100f)
            return volumePreference / 100f
        } catch (exception: ClassCastException) {
            volumePreference = Integer.parseInt(DEFAULT_VALUE_VOLUME_INCREMENTS)
            volumeIncrements = (volumePreference / 100f)
            return volumePreference / 100f
        }
    }

    companion object {
        private const val DEFAULT_VALUE_PLAYER_VOLUME = 1f
        private const val DEFAULT_VALUE_VOLUME_INCREMENTS = "5"
    }
}