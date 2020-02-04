package com.github.welshk.eirene.utils

import kotlin.math.roundToInt

class FormattingUtil {
    companion object {
        @JvmStatic
        fun volumeFormatted(volume: Float): Int {
            return ((volume * 100f).roundToInt())
        }
    }
}