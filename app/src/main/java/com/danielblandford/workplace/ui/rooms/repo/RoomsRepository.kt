package com.danielblandford.workplace.ui.rooms.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.danielblandford.workplace.network.MyAPI
import com.danielblandford.workplace.ui.rooms.data.RoomsDatabase
import com.danielblandford.workplace.ui.rooms.data.Room
import com.danielblandford.workplace.ui.rooms.data.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomsRepository(private val apiService: MyAPI, private val database: RoomsDatabase) {

    val results: LiveData<List<Room>> = Transformations.map(database.room.getLocalDBRooms()){
        it.asDomainModel()
    }

    suspend fun refreshRooms() {
        // worker thread to perform API request and save data locally
        withContext(Dispatchers.IO){
            val roomsList = apiService.getRoomsAsync().await()
            database.room.insertAll(roomsList)
        }
    }

}