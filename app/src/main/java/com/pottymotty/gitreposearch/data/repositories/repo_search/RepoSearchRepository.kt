package com.pottymotty.gitreposearch.data.repositories.repo_search

import com.pottymotty.gitreposearch.data.local.entities.relations.SearchQueryWithRepositoryAndOwner
import com.pottymotty.gitreposearch.model.GithubRepositoryWithOwner
import com.pottymotty.gitreposearch.model.RepositorySearchResult
import com.pottymotty.gitreposearch.util.FuncResult
import kotlinx.coroutines.flow.Flow

interface RepoSearchRepository {

    val currentSearchResult :  Flow<RepositorySearchResult>
    suspend fun fetchSearchResults(query: String) : FuncResult<Unit>

}