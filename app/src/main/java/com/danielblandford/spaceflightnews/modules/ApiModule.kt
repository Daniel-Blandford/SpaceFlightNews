package com.danielblandford.spaceflightnews.modules

import com.danielblandford.spaceflightnews.network.MyAPI
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    fun provideUserApi(retrofit: Retrofit): MyAPI {
        return retrofit.create(MyAPI::class.java)
    }

    single { provideUserApi(get()) }
}