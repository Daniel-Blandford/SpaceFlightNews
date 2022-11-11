package com.danielblandford.spaceflightnews.ui.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danielblandford.spaceflightnews.ui.articles.repo.ArticlesRepository
import com.danielblandford.spaceflightnews.utils.LoadingState
import kotlinx.coroutines.*
import java.lang.Exception

class ArticlesViewModel(private val repo: ArticlesRepository) : ViewModel() {

    val articlesResults = repo.results

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
                repo.refreshArticles()
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

    fun getArticles() {
        viewModelScope.launch {
            repo.refreshArticles()
        }
    }
}