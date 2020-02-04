package com.github.welshk.eirene.utils

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory
import com.google.android.exoplayer2.offline.FilteringManifestParser
import com.google.android.exoplayer2.offline.StreamKey
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistParserFactory
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.HttpDataSource
import okhttp3.OkHttpClient

class VideoUtil {
    companion object {
        /**
         * Returns the type of video in the url
         *
         */
        @JvmStatic
        fun getVideoType(uri: Uri): String {
            val path = uri.path!!
            return when {
                path.contains(".m3u8") -> Constants.Video.VIDEO_TYPE_M3U8
                path.contains(".mpd") -> Constants.Video.VIDEO_TYPE_DASH
                path.contains(".ism") -> Constants.Video.VIDEO_TYPE_SMOOTH_STREAMING
                else -> Constants.Video.VIDEO_TYPE_DEFAULT
            }
        }

        @JvmStatic
        fun getMediaSource(mediaDataSourceFactory: DataSource.Factory, uri: Uri): MediaSource {
            val contentType = getVideoType(uri)
            val mediaSource: MediaSource
            when (contentType) {
                Constants.Video.VIDEO_TYPE_M3U8 -> {
                    mediaSource = HlsMediaSource.Factory(mediaDataSourceFactory)
                        .setPlaylistParserFactory(DefaultHlsPlaylistParserFactory(getOfflineStreamKeys(uri)))
                        .createMediaSource(uri)
                    return mediaSource
                }

                Constants.Video.VIDEO_TYPE_DASH -> {
                    mediaSource = DashMediaSource.Factory(mediaDataSourceFactory)
                        .setManifestParser(FilteringManifestParser(DashManifestParser(), getOfflineStreamKeys(uri)))
                        .createMediaSource(uri)
                    return mediaSource
                }

                Constants.Video.VIDEO_TYPE_SMOOTH_STREAMING -> {
                    mediaSource = SsMediaSource.Factory(mediaDataSourceFactory)
                        .setManifestParser(FilteringManifestParser(SsManifestParser(), getOfflineStreamKeys(uri)))
                        .createMediaSource(uri)
                    return mediaSource
                }

                Constants.Video.VIDEO_TYPE_DEFAULT -> {
                    mediaSource = ExtractorMediaSource.Factory(mediaDataSourceFactory)
                        .createMediaSource(uri)
                    return mediaSource
                }

                else ->
                    throw IllegalStateException("Unsupported type: $contentType")
            }
        }

        //TODO This will be important for Offline/Downloading/Caching
        fun getOfflineStreamKeys(uri: Uri): List<StreamKey> {
            return emptyList()
        }

        /**
         * Used for online files
         */
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

        /**
         * Used for offline files
         */
        @JvmStatic
        fun buildDefaultDataSourceFactory(
            context: Context,
            userAgent: String
        ): DefaultDataSourceFactory {
            return DefaultDataSourceFactory(context, userAgent)
        }

        @JvmStatic
        fun getRenderersFactory(context: Context, noDecoders: Boolean = false): DefaultRenderersFactory {
            val defaultRenderer = DefaultRenderersFactory(context)

            if (noDecoders)
                defaultRenderer.setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF)
            else
                defaultRenderer.setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER)

            return defaultRenderer
        }
    }
}