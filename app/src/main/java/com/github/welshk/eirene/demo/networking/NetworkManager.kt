package com.github.welshk.eirene.demo.networking

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


class NetworkManager {

    object Instance {
        private var baseUrl: String? = null
        private var service: RestService? = null
        private var httpClient: OkHttpClient.Builder? = null
        private const val TIMEOUT = 20

        fun canMakeNetworkCalls(): Boolean {
            return service != null
        }

        fun getHttpClient(): OkHttpClient.Builder? {
            return httpClient
        }

        fun initService(baseUrl: String) {
            Instance.baseUrl = baseUrl
            initHttpClient()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient!!.build())
                .build()

            service = retrofit.create(RestService::class.java)
        }

        private fun initHttpClient() {
            httpClient = OkHttpClient.Builder()
            httpClient!!.connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            httpClient!!.readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            httpClient!!.writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)

            //If you want to add an interceptor, do so here (intercept the network call and possibly modify it)
            //httpClient!!.addInterceptor(NetworkInterceptor())
        }
    }
}