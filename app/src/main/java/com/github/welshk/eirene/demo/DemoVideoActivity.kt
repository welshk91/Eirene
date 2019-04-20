package com.github.welshk.eirene.demo

import com.github.welshk.eirene.demo.networking.NetworkManager
import com.github.welshk.eirene.exoplayer.EireneActivity
import okhttp3.OkHttpClient

class DemoVideoActivity : EireneActivity() {
    override fun getUrl(): String {
        return Constants.testHLS2
    }

    override fun getOkHttpClient(): OkHttpClient {
        return NetworkManager.Instance.getHttpClient()!!.build()
    }
}