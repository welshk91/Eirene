package com.github.welshk.eirene.utils

import kotlin.math.roundToInt

class FormattingUtil {

    /**
     * Since ExoPlayer provides the volume as a float between 0 & 1,
     * this converts that value into a more human-friendly integer between 0 & 100
     */
    companion object {
        @JvmStatic
        fun volumeFormatted(volume: Float): Int {
            return ((volume * 100f).roundToInt())
        }
    }
}