package com.stevenlee.swanndemo.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import com.google.android.material.snackbar.Snackbar

import com.stevenlee.swanndemo.R
import timber.log.Timber

class MainVideoFragment : Fragment() {

    private lateinit var videoView: VideoView
    private lateinit var onTouchListener: OnTouchListener

    fun setOnTouchListener(onTouchListener: OnTouchListener) {
        this.onTouchListener = onTouchListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_video, container, false)

        videoView = view.findViewById(R.id.main_video)

        // Setup VideoView
        videoView.setVideoPath(arguments?.getString("STREAM_URL"))
        videoView.setOnErrorListener { mediaPlayer, what, extra ->
            // Display a SnackBar with retry
            Snackbar.make(view, "Video Playback error", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry") {
                    videoView.setVideoPath(arguments?.getString("STREAM_URL"))
                    videoView.start()
                }.show()
            true
        }

        videoView.setOnClickListener {
            onTouchListener.onTouch()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        // Start Video
        videoView.start()

        // Force start from the beginning if it's not playing
        if(!videoView.isPlaying) {
            videoView.setVideoPath(arguments?.getString("STREAM_URL"))
            videoView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        // Pause Video
        videoView.pause()
    }

}
