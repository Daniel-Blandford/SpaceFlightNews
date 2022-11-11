package com.danielblandford.spaceflightnews.modules

import android.app.Application
import androidx.room.Room
import com.danielblandford.spaceflightnews.ui.articles.data.ArticlesDatabase

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun providesArticlesDatabase(application: Application): ArticlesDatabase {
        return Room.databaseBuilder(application, ArticlesDatabase::class.java, "articles.database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    single { providesArticlesDatabase(androidApplication()) }
}