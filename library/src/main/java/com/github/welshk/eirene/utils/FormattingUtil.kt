package com.github.welshk.eirene.utils

class FormattingUtil {
    companion object {
        @JvmStatic
        fun volumeFormatted(volume: Float): Int {
            return (volume * 100f).toInt()
        }
    }
}