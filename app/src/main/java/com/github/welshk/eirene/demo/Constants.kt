package com.github.welshk.eirene.demo

class Constants {
    companion object {
        /**
         * HLS Test URLs
         * resources: http://www.encoding.com/http-live-streaming-hls/, http://walterebert.com/playground/video/hls/
         * https://bitmovin.com/mpeg-dash-hls-examples-sample-streams/
         */
        const val testHLS = "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8"
        const val testHLS2 = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"
        const val testHLS3 = "https://mnmedias.api.telequebec.tv/m3u8/29880.m3u8"
        const val testHLS4 = "http://184.72.239.149/vod/smil:BigBuckBunny.smil/playlist.m3u8"
        const val testHLS5 = "http://www.streambox.fr/playlists/test_001/stream.m3u8"


        /**
         * Smooth Streaming
         * resources: https://zoomicon.wordpress.com/2012/09/23/collection-of-smooth-streaming-video-urls/
         */
        const val testSS = "http://smoothstreamer.doit.wisc.edu/doit-nms/BBB_carbon/BBB.ism/Manifest"
        const val testSS2 = "http://streams.smooth.vertigo.com/BigBuckBunny_30sec/bigbuck.ism/manifest"
        const val testSS3 =
            "http://streams.smooth.vertigo.com/elephantsdream/Elephants_Dream_1024-h264-st-aac.ism/manifest" //multiple audio streams and dialogs
        const val testSS4 =
            "http://playready.directtaps.net/smoothstreaming/TTLSS720VC1/To_The_Limit_720.ism/Manifest"
        const val testSS5 =
            "http://playready.directtaps.net/smoothstreaming/SSWSS720H264/ SuperSpeedway_720.ism/Manifest"
        const val testSS6 = "http://playready.directtaps.net/smoothstreaming/ISMAAACLC/Taxi3_AACLC.ism/Manifest"
        const val testSS7 = "http://playready.directtaps.net/smoothstreaming/ISMAAACHE/Taxi3_AACHE.ism/Manifest"
        const val testSS8 =
            "http://mediadl.microsoft.com/mediadl/iisnet/smoothmedia/Experience/ BigBuckBunny_720p.ism/Manifest"
        const val testSS9 =
            "http://ecn.channel9.msdn.com/o9/content/smf/smoothcontent/bbbwp7/big%20buck%20bunny.ism/manifest"
        const val testSS10 = "http://az280594.vo.msecnd.net/athadu/athadu480.ism/Manifest"
        const val testSS11 = "http://playready.directtaps.net/smoothstreaming/ISMAAACLC/Taxi3_AACLC.ism/Manifest"


        /**
         * Dash
         * resources: http://dash-mse-test.appspot.com/media.html, http://www.dash-player.com/blog/2015/04/10-free-public-mpeg-dash-teststreams-and-datasets/
         */
        const val testDASH =
            "http://yt-dash-mse-test.commondatastorage.googleapis.com/media/car-20120827-manifest.mpd"
        const val testDASH2 =
            "http://yt-dash-mse-test.commondatastorage.googleapis.com/media/car_cenc-20120827-manifest_vidonly.mpd"
        const val testDASH3 =
            "http://yt-dash-mse-test.commondatastorage.googleapis.com/media/feelings_vp9-20130806-manifest.mpd"
        const val testDASH5 =
            "http://yt-dash-mse-test.commondatastorage.googleapis.com/media/motion-20120802-manifest.mpd"
        const val testDASH6 =
            "http://yt-dash-mse-test.commondatastorage.googleapis.com/media/oops-20120802-manifest.mpd"
        const val testDASH7 =
            "http://yt-dash-mse-test.commondatastorage.googleapis.com/media/oops_cenc-20121114-manifest.mpd"
        const val testDASH8 = "http://www.bok.net/dash/tears_of_steel/cleartext/stream.mpd"
        const val testDASH9 = "http://dash.edgesuite.net/dash264/TestCases/2b/thomson-networks/2/manifest.mpd"
        const val testDASH10 =
            "http://54.171.149.139/stattodyn/statodyn.php?type=mpd&pt=1376172390&tsbd=120&origmpd=http%3A%2F%2Fdash.edgesuite.net%2Fdash264%2FTestCases%2F2b%2Fthomson-networks%2F2%2Fmanifest.mpd&php=http%3A%2F%2Fdasher.eu5.org%2Fstatodyn.php&mpd=&debug=0&hack=.mpd"
        const val testDASH11 = "http://vm2.dashif.org/livesim/modulo_10/testpic_2s/Manifest.mpd"
    }
}