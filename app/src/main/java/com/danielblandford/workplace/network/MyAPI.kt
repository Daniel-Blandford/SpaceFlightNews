package com.danielblandford.workplace.network

import com.danielblandford.workplace.ui.people.data.Person
import com.danielblandford.workplace.ui.rooms.data.Room
import retrofit2.http.GET

interface MyAPI {

    //https://61d6afbe35f71e0017c2e74b.mockapi.io/api/v1/rooms
    @GET("api/v1/rooms")
    suspend fun getRooms(): List<Room>

    //https://61d6afbe35f71e0017c2e74b.mockapi.io/api/v1/people
    @GET("api/v1/people")
    suspend fun getPeople(): List<Person>
}