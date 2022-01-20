package com.danielblandford.workplace

import android.app.Application
import com.danielblandford.workplace.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Koin : Application() {
    override fun onCreate() {
        super.onCreate()
        /**
         * Start Koin
         */
        startKoin {
            androidContext(this@Koin)
            androidLogger(Level.NONE)
            modules(listOf(viewModelModule))
        }
    }
}