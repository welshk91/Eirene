package com.github.welshk.eirene.demo.networking

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


object NetworkManager {

    private var baseUrl: String = ""
    private lateinit var service: RestService
    private lateinit var httpClient: OkHttpClient.Builder
    private const val TIMEOUT = 20

    fun getHttpClient(): OkHttpClient.Builder {
        return httpClient
    }

    fun initService(baseUrl: String) {
        this.baseUrl = baseUrl
        initHttpClient()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        service = retrofit.create(RestService::class.java)
    }

    private fun initHttpClient() {
        httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        httpClient.readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        httpClient.writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)

        //If you want to add an interceptor, do so here (intercept the network call and possibly modify it)
        //httpClient.addInterceptor(NetworkInterceptor())
    }
}