package com.danielblandford.workplace.modules

import com.danielblandford.workplace.ui.people.PeopleViewModel
import com.danielblandford.workplace.ui.rooms.RoomsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{ RoomsViewModel() }
    viewModel{ PeopleViewModel() }
}