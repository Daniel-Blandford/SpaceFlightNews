package com.danielblandford.spaceflightnews.modules

import com.danielblandford.spaceflightnews.network.MyAPI
import com.danielblandford.spaceflightnews.ui.articles.data.ArticlesDatabase
import com.danielblandford.spaceflightnews.ui.articles.repo.ArticlesRepository
import org.koin.dsl.module

val articlesRepoModule = module {

    fun provideArticlesRepository(api: MyAPI, database: ArticlesDatabase): ArticlesRepository {
        return ArticlesRepository(api, database)
    }

    single {
        provideArticlesRepository(get(), get())
    }

}