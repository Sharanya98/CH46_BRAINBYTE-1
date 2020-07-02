package com.brainbyte.healthareana

import android.app.Application
import com.brainbyte.healthareana.di.AppComponent
import com.brainbyte.healthareana.di.DaggerAppComponent
import timber.log.Timber

class HealthArenaApplication : Application() {

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        DaggerAppComponent.factory().create(applicationContext as Application)
    }

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}