package com.danielblandford.workplace.ui.rooms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danielblandford.workplace.ui.rooms.repo.RoomsRepository
import com.danielblandford.workplace.utils.LoadingState
import kotlinx.coroutines.*
import java.lang.Exception

class RoomsViewModel(private val repo: RoomsRepository) : ViewModel() {

    val roomResults = repo.results

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    private val viewModelJob = SupervisorJob()
    private val viewModelScope  = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        refreshFromRepository()
    }

    private fun refreshFromRepository() {
        viewModelScope.launch {
            try {
                _loadingState.value = LoadingState.LOADING
                repo.refreshRooms()
                _loadingState.value = LoadingState.LOADED
            } catch (networkError: Exception){
                _loadingState.value = LoadingState.error(networkError.message)
            }
        }
    }

    override fun onCleared(){
        super.onCleared()
        viewModelScope.cancel()
    }

    fun getRooms() {
        viewModelScope.launch {
            repo.refreshRooms()
        }
    }
}