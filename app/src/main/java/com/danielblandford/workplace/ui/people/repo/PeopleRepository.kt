package com.danielblandford.workplace.ui.people.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.danielblandford.workplace.network.MyAPI
import com.danielblandford.workplace.ui.people.data.PeopleDatabase
import com.danielblandford.workplace.ui.people.data.Person
import com.danielblandford.workplace.ui.people.data.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PeopleRepository(private val apiService: MyAPI, private val database: PeopleDatabase) {

    val results: LiveData<List<Person>> = Transformations.map(database.people.getLocalDBPeople()){
        it.asDomainModel()
    }

    suspend fun refreshPeople() {
        // worker thread to perform API request and save data locally
        withContext(Dispatchers.IO){
            val peopleList = apiService.getPeopleAsync().await()
            database.people.insertAll(peopleList)
        }
    }

}