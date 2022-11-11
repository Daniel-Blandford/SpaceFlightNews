package com.danielblandford.spaceflightnews.network

import com.danielblandford.spaceflightnews.ui.articles.data.Article
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface MyAPI {

    @GET("articles")
    fun getArticlesAsync(): Deferred<List<Article>>
}