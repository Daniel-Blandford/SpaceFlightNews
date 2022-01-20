package com.danielblandford.workplace.ui.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danielblandford.workplace.ui.people.repo.PeopleRepository
import com.danielblandford.workplace.utils.LoadingState
import kotlinx.coroutines.*
import java.lang.Exception

class PeopleViewModel(private val repo: PeopleRepository) : ViewModel() {

    val peopleResults = repo.results

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
                repo.refreshPeople()
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

    fun getPeople() {
        viewModelScope.launch {
            repo.refreshPeople()
        }
    }
}