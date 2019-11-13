package com.stevenlee.swanndemo.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Lazy global init of retrofitService
val retrofitService by lazy {
    RetrofitBuilder.create()
}

class RetrofitBuilder {
    companion object {
        fun create(): RetrofitService {
            val baseUrl = "https://hw1ym521u8.execute-api.us-west-2.amazonaws.com"
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RetrofitService::class.java)
        }
    }
}