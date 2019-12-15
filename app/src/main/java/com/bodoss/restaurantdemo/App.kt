package com.bodoss.restaurantdemo

import android.app.Application
import timber.log.Timber

class App : Application() {

    companion object {
        private lateinit var instance: App
        fun get() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}