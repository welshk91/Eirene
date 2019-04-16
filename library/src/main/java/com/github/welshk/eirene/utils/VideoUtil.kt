package com.github.welshk.eirene.utils

import android.net.Uri

import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import okhttp3.OkHttpClient
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistParserFactory
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DataSource

class VideoUtil {
    companion object {
        /**
         * Returns the type of video in the url
         *
         */
        @JvmStatic
        fun getVideoType(url: String): String {
            return when {
                url.contains(".m3u8") -> Constants.Video.VIDEO_TYPE_M3U8
                url.contains(".mpd") -> Constants.Video.VIDEO_TYPE_DASH
                url.contains(".ism") -> Constants.Video.VIDEO_TYPE_SMOOTH_STREAMING
                else -> Constants.Video.VIDEO_TYPE_DEFAULT
            }
        }

        @JvmStatic
        fun getMediaSource(mediaDataSourceFactory: DataSource.Factory, url: String): MediaSource {
            val contentType = getVideoType(url)
            val mediaSource: MediaSource
            when (contentType) {
                Constants.Video.VIDEO_TYPE_M3U8 -> {
                    mediaSource = HlsMediaSource.Factory(mediaDataSourceFactory)
                        .setPlaylistParserFactory(DefaultHlsPlaylistParserFactory())
                        .createMediaSource(Uri.parse(url))
                    return mediaSource
                }

                Constants.Video.VIDEO_TYPE_DEFAULT -> {
                    mediaSource = ExtractorMediaSource.Factory(mediaDataSourceFactory)
                        .createMediaSource(Uri.parse(url))
                    return mediaSource
                }

                else ->
                    throw IllegalStateException("Unsupported type: $contentType")
            }
        }

        @JvmStatic
        fun buildHttpDataSourceFactory(
            httpClient: OkHttpClient,
            userAgent: String,
            bandwidthMeter: DefaultBandwidthMeter
        ): HttpDataSource.Factory {
            return OkHttpDataSourceFactory(
                httpClient,
                userAgent,
                bandwidthMeter
            )
        }
    }
}