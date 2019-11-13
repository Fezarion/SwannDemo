package com.stevenlee.swanndemo.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stevenlee.swanndemo.network.models.VideoStreams
import com.stevenlee.swanndemo.network.retrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository {

    // VideoStream URLs from the server as LiveData
    private var streams = MutableLiveData<VideoStreams>()

    private lateinit var onFailureCallback: OnFailureCallback

    /**
     * Get VideoStreams using retrofit
     * @return LiveData of VideoStreams
     */
    fun getStreams() : LiveData<VideoStreams> {
        retrofitService.getVideoStreams().enqueue(object : Callback<VideoStreams> {
            override fun onFailure(call: Call<VideoStreams>, t: Throwable) {
                onFailureCallback(t.toString())
            }

            override fun onResponse(call: Call<VideoStreams>, response: Response<VideoStreams>) {
                if (response.isSuccessful) {
                    streams.value = response.body()
                } else {
                    onFailureCallback(response.toString())
                }
            }
        })

        return streams
    }

    fun setOnFailureCallback(onFailureCallback: OnFailureCallback) {
        this.onFailureCallback = onFailureCallback
    }

    /**
     * @param error message
     */
    fun onFailureCallback(error: String) {
        onFailureCallback.onFailure(error)
    }

}