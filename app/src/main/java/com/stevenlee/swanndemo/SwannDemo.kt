package com.stevenlee.swanndemo

import android.app.Application
import timber.log.Timber

class SwannDemo : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}