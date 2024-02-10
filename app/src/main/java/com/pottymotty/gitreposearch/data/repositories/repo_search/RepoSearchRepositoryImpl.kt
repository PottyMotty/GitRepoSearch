package com.pottymotty.gitreposearch.data.repositories.repo_search

import com.pottymotty.gitreposearch.data.api.NetworkDataSource
import com.pottymotty.gitreposearch.data.api.call_response.NetworkResponse
import com.pottymotty.gitreposearch.data.local.dao.SearchResultDao
import com.pottymotty.gitreposearch.data.local.entities.OwnerEntity
import com.pottymotty.gitreposearch.data.local.entities.RepositoryEntity
import com.pottymotty.gitreposearch.data.local.entities.SearchQueryEntity
import com.pottymotty.gitreposearch.data.local.entities.relations.SearchQueryRepositoryCrossRef
import com.pottymotty.gitreposearch.data.local.entities.relations.SearchQueryWithRepositoryAndOwner
import com.pottymotty.gitreposearch.data.local.entities.toEntity
import com.pottymotty.gitreposearch.data.util.handleResult
import com.pottymotty.gitreposearch.mapper.toModel
import com.pottymotty.gitreposearch.model.GithubRepositoryWithOwner
import com.pottymotty.gitreposearch.model.RepositorySearchResult
import com.pottymotty.gitreposearch.util.FuncResult
import com.pottymotty.gitreposearch.util.withoutType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.time.Instant
import kotlin.time.measureTimedValue

class RepoSearchRepositoryImpl(
    private val networkDataSource: NetworkDataSource,
    private val searchResultDao: SearchResultDao
) : RepoSearchRepository {

    private val currentSearch = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val currentSearchResult: Flow<RepositorySearchResult> =
        currentSearch.filterNotNull().flatMapLatest {queryString ->
            searchResultDao.getResultsForQuery(queryString).map { result->
                RepositorySearchResult(
                    searchQuery = queryString,
                    totalCount = result.query.totalResultCount,
                    items = result.toModel()
                )
            }
        }

    override suspend fun fetchSearchResults(query: String): FuncResult<Unit> =
        withContext(Dispatchers.IO) {
            currentSearch.update { query }
            val result = networkDataSource.fetchRepositorySearchResults(query)
            result.handleResult(
                onSuccess = { searchResultDto ->
                    val queryResultEntity = SearchQueryEntity(
                        query = query,
                        timestamp = Instant.now().toString(),
                        totalResultCount = searchResultDto.totalCount
                    )
                    val ownersEntities = searchResultDto.items.map {
                        it.owner
                    }.toSet().map {
                        it.toEntity()
                    }
                    val reposEntities = searchResultDto.items.map {
                        it.toEntity()
                    }
                    searchResultDao.deleteOutdatedData(query)
                    searchResultDao.insertDataFromSearchResult(
                        searchQuery = queryResultEntity,
                        repositories = reposEntities,
                        owners = ownersEntities
                    )
                }
            ).withoutType()
        }


}

