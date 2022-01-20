package com.danielblandford.workplace.modules

import com.danielblandford.workplace.network.MyAPI
import com.danielblandford.workplace.ui.rooms.data.RoomsDatabase
import com.danielblandford.workplace.ui.rooms.repo.RoomsRepository
import org.koin.dsl.module

val roomsRepoModule = module {

    fun provideRoomsRepository(api: MyAPI, database: RoomsDatabase): RoomsRepository {
        return RoomsRepository(api, database)
    }

    single {
        provideRoomsRepository(get(), get())
    }

}