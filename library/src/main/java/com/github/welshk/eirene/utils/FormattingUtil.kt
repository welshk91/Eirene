package com.github.welshk.eirene.utils

class FormattingUtil {
    companion object {
        @JvmStatic
        fun volumeFormatted(volume: Float): String {
            return String.format("%.0f", volume * 100f)
        }
    }
}