package com.stevenlee.swanndemo.network

import com.stevenlee.swanndemo.network.models.VideoStreams
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {

    @GET("/beta/list-stream")
    fun getVideoStreams() : Call<VideoStreams>

}