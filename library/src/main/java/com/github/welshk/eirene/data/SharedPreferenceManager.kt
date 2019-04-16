package com.github.welshk.eirene.data

import com.github.welshk.eirene.R
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager


class SharedPreferenceManager {
    object Instance {
        private const val DEFAULT_VALUE_PLAYER_VOLUME = 1f
        private const val DEFAULT_VALUE_VOLUME_INCREMENTS = "5"

        private var sharedPreferences: SharedPreferences? = null

        fun initializeSharedPreferences(context: Context) {
            if (sharedPreferences == null) {
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            }
        }

        fun getSharedPreferences(): SharedPreferences? {
            return sharedPreferences
        }

        fun getEditor(): SharedPreferences.Editor {
            return sharedPreferences!!.edit()
        }

        fun setVolume(context: Context, volume: Float) {
            getEditor().putFloat(context.getString(R.string.pref_key_player_volume), volume).apply()
        }

        fun getVolume(context: Context): Float {
            return getSharedPreferences()!!.getFloat(
                context.getString(R.string.pref_key_player_volume),
                DEFAULT_VALUE_PLAYER_VOLUME
            )
        }

        fun getVolumeIncrements(context: Context): Float {
            var volumePreference: Int

            try {
                volumePreference = Integer.parseInt(
                    getSharedPreferences()?.getString(
                        context.getString(R.string.pref_key_player_volume_increments),
                        DEFAULT_VALUE_VOLUME_INCREMENTS
                    )
                )
                return volumePreference / 100f
            } catch (exception: NumberFormatException) {
                volumePreference = Integer.parseInt(DEFAULT_VALUE_VOLUME_INCREMENTS)
                setVolumeIncrements(context, volumePreference / 100f)
                return volumePreference / 100f
            } catch (exception: ClassCastException) {
                volumePreference = Integer.parseInt(DEFAULT_VALUE_VOLUME_INCREMENTS)
                setVolumeIncrements(context, volumePreference / 100f)
                return volumePreference / 100f
            }

        }

        fun setVolumeIncrements(context: Context, volumeIncrements: Float) {
            getEditor().putFloat(context.getString(R.string.pref_key_player_volume_increments), volumeIncrements)
                .apply()
        }
    }
}