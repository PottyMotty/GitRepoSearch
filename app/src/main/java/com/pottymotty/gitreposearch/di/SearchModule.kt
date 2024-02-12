package com.pottymotty.gitreposearch.di

import com.pottymotty.gitreposearch.data.SearchRemoteMediator
import com.pottymotty.gitreposearch.data.repositories.repo_search.RepoSearchRepository
import com.pottymotty.gitreposearch.data.repositories.repo_search.RepoSearchRepositoryImpl
import org.koin.dsl.module

val searchModule = module {
    single<RepoSearchRepository> {
        RepoSearchRepositoryImpl(
            searchResultDao = get(),
        )
    }

    factory {params->
        SearchRemoteMediator(
            query = params.get(), database =get(), networkDataSource =get()
        )
    }
}