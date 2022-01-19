package com.danielblandford.workplace.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyNetwork {

    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://61d6afbe35f71e0017c2e74b.mockapi.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyAPI::class.java)
    }
}