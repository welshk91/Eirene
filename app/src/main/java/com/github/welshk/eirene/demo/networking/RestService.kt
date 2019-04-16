package com.github.welshk.eirene.demo.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface RestService {
    @GET
    fun getMediaUrl(@Url url: String): Call<String>
}
