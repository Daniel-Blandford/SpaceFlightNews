package com.danielblandford.spaceflightnews.modules

import com.danielblandford.spaceflightnews.ui.articles.ArticlesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{ ArticlesViewModel(get()) }
}