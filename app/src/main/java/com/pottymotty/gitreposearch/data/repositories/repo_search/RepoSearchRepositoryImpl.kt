package com.pottymotty.gitreposearch.data.repositories.repo_search

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pottymotty.gitreposearch.data.SearchRemoteMediator
import com.pottymotty.gitreposearch.data.api.NetworkDataSource
import com.pottymotty.gitreposearch.data.local.dao.SearchResultDao
import com.pottymotty.gitreposearch.data.local.database.GithubDatabase
import com.pottymotty.gitreposearch.data.local.entities.relations.RepositoryWithOwner
import com.pottymotty.gitreposearch.mapper.toModel
import com.pottymotty.gitreposearch.model.GithubRepositoryWithOwner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class RepoSearchRepositoryImpl(
    private val searchResultDao: SearchResultDao,
) : RepoSearchRepository, KoinComponent {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPaginatedSearchResult(
        query: String,
        pageSize: Int,
        prefetchDistance: Int,
    ): Flow<PagingData<RepositoryWithOwner>> {
        val remoteMediator : SearchRemoteMediator by inject { parametersOf(query) }
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = prefetchDistance,
                initialLoadSize = pageSize
            ),
            remoteMediator = remoteMediator
        ) {
            searchResultDao.getResultsForQuery(query)
        }.flow
    }

    override fun getDetailItemForRepository(id: Long): Flow<GithubRepositoryWithOwner> {
        return searchResultDao.getRepository(id).map { it.toModel() }
    }
}

