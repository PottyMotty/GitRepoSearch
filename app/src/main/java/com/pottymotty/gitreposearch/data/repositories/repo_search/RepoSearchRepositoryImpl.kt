package com.pottymotty.gitreposearch.data.repositories.repo_search

import com.pottymotty.gitreposearch.data.api.NetworkDataSource
import timber.log.Timber

class RepoSearchRepositoryImpl(
    private val networkDataSource: NetworkDataSource
) : RepoSearchRepository {
    override suspend fun fetchSearchResults(query: String) {
        val result = networkDataSource.fetchRepositorySearchResults(query)
        Timber.d("FETCH_RESULT: $result")
    }
}