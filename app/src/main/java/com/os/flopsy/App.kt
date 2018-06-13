package com.os.flopsy

import android.app.Application
import timber.log.Timber

/**
 * Created by Omar on 11-Jun-18 4:49 PM.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}