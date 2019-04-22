package com.github.welshk.eirene.demo

import android.net.Uri
import com.github.welshk.eirene.demo.networking.NetworkManager
import com.github.welshk.eirene.exoplayer.EireneFragment
import okhttp3.OkHttpClient

/**
 * Currently not being used in the demo
 */
class DemoVideoFragment : EireneFragment() {
    override fun getUri(): Uri {
        return Uri.parse(Constants.testHLS)
    }

    override fun getOkHttpClient(): OkHttpClient {
        return NetworkManager.Instance.getHttpClient()!!.build()
    }
}