package com.danielblandford.workplace.modules

import android.app.Application
import androidx.room.Room
import com.danielblandford.workplace.ui.people.data.PeopleDatabase
import com.danielblandford.workplace.ui.rooms.data.RoomsDatabase

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun providesPeopleDatabase(application: Application): PeopleDatabase {
        return Room.databaseBuilder(application, PeopleDatabase::class.java, "people.database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    fun providesRoomsDatabase(application: Application): RoomsDatabase {
        return Room.databaseBuilder(application, RoomsDatabase::class.java, "rooms.database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    single { providesPeopleDatabase(androidApplication()) }

    single { providesRoomsDatabase(androidApplication()) }
}