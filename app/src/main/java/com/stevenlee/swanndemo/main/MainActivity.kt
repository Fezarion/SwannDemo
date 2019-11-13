package com.stevenlee.swanndemo.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.stevenlee.swanndemo.EmptyActivity
import com.stevenlee.swanndemo.R
import com.stevenlee.swanndemo.network.models.VideoStreams
import com.stevenlee.swanndemo.network.retrofitService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainActivity : AppCompatActivity(), OnFailureCallback, OnTouchListener {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainActivityPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup ViewPagerAdapter
        adapter = MainActivityPagerAdapter(supportFragmentManager)

        main_view_pager.adapter = adapter
        main_video_control.setupWithViewPager(main_view_pager)

        // Launch new activity when Settings icon is clicked
        main_settings.setOnClickListener {
            val intent = Intent(this, EmptyActivity::class.java)
            startActivity(intent)
        }

        // Setup ViewModel for MainActivity
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.getStreams().observe(this,
            Observer<VideoStreams> {
                // Remove progress bar
                main_progress_bar.visibility = GONE
                // Notify adapter
                adapter.setStreams(it)
            })

        mainViewModel.setOnFailureCallback(this)
    }

    /**
     * If the mainViewModel's stream is empty then retry the network request
     */
    override fun onStart() {
        super.onStart()
        if (mainViewModel.getStreams().value == null) {
            mainViewModel.retry()
        }
    }

    /**
     * Show a SnackBar on failure and have the option to retry
     * @param error message to display to log
     */
    override fun onFailure(error: String) {
        Snackbar.make(main_container, "Cannot get Video URLs", Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") {
                mainViewModel.retry()
            }.show()
        Timber.e(error)
    }

    /**
     * Toggle settings and video controls visibility
     */
    override fun onTouch() {
        if (main_settings.isVisible) {
            main_settings.visibility = GONE
            main_video_control.visibility = GONE
        } else {
            main_settings.visibility = VISIBLE
            main_video_control.visibility = VISIBLE
        }
    }

    /**
     * SetOnTouchListener onAttach
     */
    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        if (fragment is MainVideoFragment) {
            fragment.setOnTouchListener(this)
        }
    }
}
