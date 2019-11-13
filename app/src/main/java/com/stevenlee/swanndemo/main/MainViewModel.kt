package com.stevenlee.swanndemo.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.stevenlee.swanndemo.network.models.VideoStreams

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MainRepository()
    private var streams = repository.getStreams()

    /**
     * @return LiveData of VideoStreams
     */
    fun getStreams() : LiveData<VideoStreams> {
        return streams
    }

    /**
     * Retry getting the VideoStream
     */
    fun retry() {
        repository.getStreams()
    }

    fun setOnFailureCallback(onFailureCallback: OnFailureCallback) {
        repository.setOnFailureCallback(onFailureCallback)
    }

}