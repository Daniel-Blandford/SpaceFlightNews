package com.danielblandford.spaceflightnews.ui.articles.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.danielblandford.spaceflightnews.network.MyAPI
import com.danielblandford.spaceflightnews.ui.articles.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticlesRepository(private val apiService: MyAPI, private val database: ArticlesDatabase) {

    val results: LiveData<List<Article>> = Transformations.map(database.articles.getLocalDBArticles()){
        it.asDomainModel()
    }

    suspend fun refreshArticles() {
        // worker thread to perform API request and save data locally
        withContext(Dispatchers.IO){
            val articleList = apiService.getArticlesAsync().await()
            database.articles.insertAll(articleList)
        }
    }

}