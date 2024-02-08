package com.pottymotty.gitreposearch.data.repositories.repo_search

import com.pottymotty.gitreposearch.model.RepositorySearchResult

interface RepoSearchRepository {

    suspend fun fetchSearchResults(query: String)
}