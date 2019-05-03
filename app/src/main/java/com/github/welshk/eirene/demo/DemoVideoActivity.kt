package com.github.welshk.eirene.demo

import android.net.Uri
import com.github.welshk.eirene.demo.networking.NetworkManager
import com.github.welshk.eirene.exoplayer.EireneActivity
import okhttp3.OkHttpClient

class DemoVideoActivity : EireneActivity() {
    override fun getUri(): Uri {
        return Uri.parse(Constants.testDASH7)
    }

    override fun getOkHttpClient(): OkHttpClient {
        return NetworkManager.Instance.getHttpClient()!!.build()
    }
}