package com.danielblandford.workplace.ui.people

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielblandford.workplace.network.MyNetwork
import com.danielblandford.workplace.ui.people.data.Person
import kotlinx.coroutines.launch

class PeopleViewModel : ViewModel() {

    val peopleResponseList: MutableLiveData<List<Person>> = MutableLiveData()

    fun getPeople() {
        viewModelScope.launch {
            peopleResponseList.value = MyNetwork.retrofit.getPeople()
        }
    }
}