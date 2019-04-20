package com.github.welshk.eirene.demo

import com.github.welshk.eirene.demo.networking.NetworkManager
import com.github.welshk.eirene.exoplayer.EireneFragment
import okhttp3.OkHttpClient

/**
 * Currently not being used in the demo
 */
class DemoVideoFragment : EireneFragment() {
    override fun getUrl(): String {
        return Constants.testHLS
    }

    override fun getOkHttpClient(): OkHttpClient {
        return NetworkManager.Instance.getHttpClient()!!.build()
    }
}