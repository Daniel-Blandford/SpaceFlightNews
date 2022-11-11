package com.danielblandford.spaceflightnews

import android.app.Application
import androidx.multidex.MultiDex
import com.danielblandford.spaceflightnews.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Koin : Application() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        /**
         * Start Koin
         */
        startKoin {
            androidContext(this@Koin)
            androidLogger(Level.NONE)
            modules(listOf(viewModelModule, networkModule, articlesRepoModule, apiModule, databaseModule))
        }
    }
}