package com.github.welshk.eirene.data

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)

    private fun getEditor(): SharedPreferences.Editor {
        return prefs.edit()
    }

    var volume: Float
        get() = prefs.getFloat(VOLUME_KEY, DEFAULT_VALUE_PLAYER_VOLUME)
        set(value) = getEditor().putFloat(VOLUME_KEY, value).apply()

    var volumeIncrements: Float
        get() = getVolumeIncrementsParsing()
        set(value) = getEditor().putFloat(VOLUME_INCREMENTS_KEY, value).apply()

    private fun getVolumeIncrementsParsing(): Float {
        val volumePreferenceString =
            prefs.getString(VOLUME_INCREMENTS_KEY, DEFAULT_VALUE_VOLUME_INCREMENTS)

        volumePreferenceString?.let {
            return try {
                var volumePreference = Integer.parseInt(it)
                volumePreference / 100f
            } catch (exception: NumberFormatException) {
                defaultVolumeIncrement()
            } catch (exception: ClassCastException) {
                defaultVolumeIncrement()
            }
        }

        return defaultVolumeIncrement()
    }

    private fun defaultVolumeIncrement(): Float {
        var volumePreference = Integer.parseInt(DEFAULT_VALUE_VOLUME_INCREMENTS)
        volumeIncrements = (volumePreference / 100f)
        return volumePreference / 100f
    }

    var lastKnownPosition: Long
        get() = prefs.getLong(POSITION_KEY, DEFAULT_VALUE_POSITION)
        set(value) = getEditor().putLong(POSITION_KEY, value).apply()

    var lastKnownPlayWhenReady: Boolean
        get() = prefs.getBoolean(PLAY_WHEN_READY_KEY, DEFAULT_VALUE_PLAY_WHEN_READY)
        set(value) = getEditor().putBoolean(PLAY_WHEN_READY_KEY, value).apply()

    var lastKnownCurrentWindow: Int
        get() = prefs.getInt(CURRENT_WINDOW_KEY, DEFAULT_VALUE_CURRENT_WINDOW)
        set(value) = getEditor().putInt(CURRENT_WINDOW_KEY, value).apply()

    companion object {
        private const val PRIVATE_MODE = 0
        private const val PREF_NAME = "Eirene-Preferences"

        private const val VOLUME_KEY = "player_volume"
        private const val VOLUME_INCREMENTS_KEY = "player_volume_increments"
        private const val POSITION_KEY = "player_position"
        private const val PLAY_WHEN_READY_KEY = "player_play_when_ready"
        private const val CURRENT_WINDOW_KEY = "player_current_window"

        const val DEFAULT_VALUE_PLAYER_VOLUME = 1f
        const val DEFAULT_VALUE_VOLUME_INCREMENTS = "5"
        const val DEFAULT_VALUE_POSITION = 0L
        const val DEFAULT_VALUE_PLAY_WHEN_READY = true
        const val DEFAULT_VALUE_CURRENT_WINDOW = 0
    }
}