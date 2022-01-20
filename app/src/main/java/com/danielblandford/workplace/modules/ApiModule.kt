package com.danielblandford.workplace.modules

import com.danielblandford.workplace.network.MyAPI
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    fun provideUserApi(retrofit: Retrofit): MyAPI {
        return retrofit.create(MyAPI::class.java)
    }

    single { provideUserApi(get()) }
}