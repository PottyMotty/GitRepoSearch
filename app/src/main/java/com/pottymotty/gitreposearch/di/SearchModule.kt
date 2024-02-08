package com.pottymotty.gitreposearch.di

import com.pottymotty.gitreposearch.data.repositories.repo_search.RepoSearchRepository
import com.pottymotty.gitreposearch.data.repositories.repo_search.RepoSearchRepositoryImpl
import com.pottymotty.gitreposearch.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    single<RepoSearchRepository> {
        RepoSearchRepositoryImpl(
            networkDataSource = get()
        )
    }
    viewModel {
        MainViewModel(
            get()
        )
    }
}