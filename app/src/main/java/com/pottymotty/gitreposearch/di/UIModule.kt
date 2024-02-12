package com.pottymotty.gitreposearch.di

import com.pottymotty.gitreposearch.ui.screens.detail.DetailViewModel
import com.pottymotty.gitreposearch.ui.screens.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module{
    viewModel {
        ListViewModel(
            initialSearchQuery = get(),
            repoSearchRepository = get(),
        )
    }
    viewModel {
        DetailViewModel(
            repoSearchRepository =get(), repositoryId = get()
        )
    }
}
