package com.github.welshk.eirene.demo

class Constants {
    companion object {
        /**
         * HLS Test URLs
         * resources: http://www.encoding.com/http-live-streaming-hls/, http://walterebert.com/playground/video/hls/
         * https://bitmovin.com/mpeg-dash-hls-examples-sample-streams/
         */
        const val testHLS =
            "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8"
        const val testHLS2 = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"
        const val testHLS3 = "https://mnmedias.api.telequebec.tv/m3u8/29880.m3u8"
        const val testHLS4 = "http://184.72.239.149/vod/smil:BigBuckBunny.smil/playlist.m3u8"
        const val testHLS5 = "http://www.streambox.fr/playlists/test_001/stream.m3u8"


        /**
         * Smooth Streaming
         * resources: https://zoomicon.wordpress.com/2012/09/23/collection-of-smooth-streaming-video-urls/
         */
        const val testSS =
            "http://smoothstreamer.doit.wisc.edu/doit-nms/BBB_carbon/BBB.ism/Manifest"
        const val testSS2 =
            "http://streams.smooth.vertigo.com/BigBuckBunny_30sec/bigbuck.ism/manifest"
        const val testSS3 =
            "http://streams.smooth.vertigo.com/elephantsdream/Elephants_Dream_1024-h264-st-aac.ism/manifest" //multiple audio streams and dialogs
        const val testSS4 =
            "http://playready.directtaps.net/smoothstreaming/TTLSS720VC1/To_The_Limit_720.ism/Manifest"
        const val testSS5 =
            "http://playready.directtaps.net/smoothstreaming/SSWSS720H264/ SuperSpeedway_720.ism/Manifest"
        const val testSS6 =
            "http://playready.directtaps.net/smoothstreaming/ISMAAACLC/Taxi3_AACLC.ism/Manifest"
        const val testSS7 =
            "http://playready.directtaps.net/smoothstreaming/ISMAAACHE/Taxi3_AACHE.ism/Manifest"
        const val testSS8 =
            "http://mediadl.microsoft.com/mediadl/iisnet/smoothmedia/Experience/ BigBuckBunny_720p.ism/Manifest"
        const val testSS9 =
            "http://ecn.channel9.msdn.com/o9/content/smf/smoothcontent/bbbwp7/big%20buck%20bunny.ism/manifest"
        const val testSS10 = "http://az280594.vo.msecnd.net/athadu/athadu480.ism/Manifest"
        const val testSS11 =
            "http://playready.directtaps.net/smoothstreaming/ISMAAACLC/Taxi3_AACLC.ism/Manifest"

        //From ExoPlayer sample app; these are the only SS that work for some reason...
        const val testSS12 =
            "https://playready.directtaps.net/smoothstreaming/SSWSS720H264/SuperSpeedway_720.ism"

        //DRM
        const val testSS13 =
            "https://playready.directtaps.net/smoothstreaming/SSWSS720H264PR/SuperSpeedway_720.ism"


        /**
         * Dash
         * resources: http://testassets.dashif.org/#testvector/list
         */
        const val testDASH =
            "http://dash.akamaized.net/dash264/TestCasesUHD/2b/11/MultiRate.mpd"
        const val testDASH2 =
            "http://dash.akamaized.net/dash264/TestCasesUHD/2a/11/MultiRate.mpd"
        const val testDASH3 =
            "http://dash.akamaized.net/dash264/TestCasesIOP33/adapatationSetSwitching/5/manifest.mpd"
        const val testDASH4 =
            "http://dash.akamaized.net/dash264/TestCasesIOP41/MultiTrack/alternative_content/2/manifest_alternative_content_ondemand.mpd"
        const val testDASH5 =
            "http://dash.akamaized.net/dash264/TestCasesIOP41/MultiTrack/associative_content/1/manifest_associated_content_live.mpd"
        const val testDASH6 =
            "http://dash.akamaized.net/dash264/TestCasesHD/2b/qualcomm/1/MultiResMPEG2.mpd"
        const val testDASH7 = "http://www.bok.net/dash/tears_of_steel/cleartext/stream.mpd"
    }
}