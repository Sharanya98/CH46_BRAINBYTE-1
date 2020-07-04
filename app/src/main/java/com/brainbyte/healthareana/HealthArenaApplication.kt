package com.brainbyte.healthareana

import android.app.Application
import timber.log.Timber

class HealthArenaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}