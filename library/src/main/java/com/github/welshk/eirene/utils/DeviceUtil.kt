package com.github.welshk.eirene.utils

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_IMMERSIVE
import android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN

class DeviceUtil {
    companion object {
        private var mSystemUiVisibility: Int = 0

        /**
         * Hides system UI by setting the appropriate flags
         */
        @JvmStatic
        @Throws(NullPointerException::class)
        fun hideSystemUi(context: Context) {
            mSystemUiVisibility = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                (SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or SYSTEM_UI_FLAG_FULLSCREEN
                        or SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or SYSTEM_UI_FLAG_IMMERSIVE)
            } else {
                (SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or SYSTEM_UI_FLAG_FULLSCREEN
                        or SYSTEM_UI_FLAG_HIDE_NAVIGATION)
            }

            (context as AppCompatActivity).supportActionBar?.hide()
            context.window.decorView.systemUiVisibility = mSystemUiVisibility
        }

        /**
         * Shows system UI by setting the appropriate flags
         */
        @JvmStatic
        @Throws(NullPointerException::class)
        fun showSystemUi(context: Context) {
            mSystemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN and SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

            (context as AppCompatActivity).supportActionBar?.show()
            context.window.decorView.systemUiVisibility = mSystemUiVisibility
        }
    }
}