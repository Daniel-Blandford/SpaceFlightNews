package com.danielblandford.workplace.ui.rooms

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielblandford.workplace.ui.rooms.data.Room
import com.danielblandford.workplace.network.MyNetwork
import kotlinx.coroutines.launch

class RoomsViewModel : ViewModel() {

    val roomsResponseList: MutableLiveData<List<Room>> = MutableLiveData()

    fun getRooms() {
        viewModelScope.launch {
            roomsResponseList.value = MyNetwork.retrofit.getRooms()
        }
    }
}