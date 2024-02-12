package com.pottymotty.gitreposearch.data.repositories.repo_search

import androidx.paging.PagingData
import com.pottymotty.gitreposearch.data.local.entities.relations.RepositoryWithOwner
import com.pottymotty.gitreposearch.model.GithubRepositoryWithOwner
import com.pottymotty.gitreposearch.util.FuncResult
import kotlinx.coroutines.flow.Flow

interface RepoSearchRepository {
    fun getPaginatedSearchResult(
        query: String,
        pageSize: Int,
        prefetchDistance: Int
    ): Flow<PagingData<RepositoryWithOwner>>


    fun getDetailItemForRepository(id:Long) : Flow<GithubRepositoryWithOwner>
}