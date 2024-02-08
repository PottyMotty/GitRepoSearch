package com.pottymotty.gitreposearch.data.repositories.repo_search

import com.pottymotty.gitreposearch.data.local.entities.relations.SearchQueryWithRepositoryAndOwner
import com.pottymotty.gitreposearch.model.RepositorySearchResult
import kotlinx.coroutines.flow.Flow

interface RepoSearchRepository {

    val currentSearchResult : Flow<SearchQueryWithRepositoryAndOwner>
    suspend fun fetchSearchResults(query: String)

}