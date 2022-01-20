package com.danielblandford.workplace.modules

import com.danielblandford.workplace.network.MyAPI
import com.danielblandford.workplace.ui.people.data.PeopleDatabase
import com.danielblandford.workplace.ui.people.repo.PeopleRepository
import org.koin.dsl.module

val peopleRepoModule = module {

    fun providePeopleRepository(api: MyAPI, database: PeopleDatabase): PeopleRepository {
        return PeopleRepository(api, database)
    }

    single {
        providePeopleRepository(get(), get())
    }

}