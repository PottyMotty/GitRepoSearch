package com.pottymotty.gitreposearch.data.repositories.repo_search

import com.pottymotty.gitreposearch.data.api.NetworkDataSource
import com.pottymotty.gitreposearch.data.api.call_response.NetworkResponse
import com.pottymotty.gitreposearch.data.local.dao.SearchResultDao
import com.pottymotty.gitreposearch.data.local.entities.OwnerEntity
import com.pottymotty.gitreposearch.data.local.entities.RepositoryEntity
import com.pottymotty.gitreposearch.data.local.entities.SearchQueryEntity
import com.pottymotty.gitreposearch.data.local.entities.relations.SearchQueryRepositoryCrossRef
import com.pottymotty.gitreposearch.data.local.entities.relations.SearchQueryWithRepositoryAndOwner
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import timber.log.Timber

class RepoSearchRepositoryImpl(
    private val networkDataSource: NetworkDataSource,
    private val searchResultDao: SearchResultDao
) : RepoSearchRepository {

    private val currentSearch = MutableStateFlow<String?>(null)
    @OptIn(ExperimentalCoroutinesApi::class)
    override val currentSearchResult: Flow<SearchQueryWithRepositoryAndOwner> = currentSearch.filterNotNull().flatMapLatest {
        searchResultDao.getResultsForQuery(it)
    }



    override suspend fun fetchSearchResults(query: String) {
        val result = networkDataSource.fetchRepositorySearchResults(query)
        Timber.d("FETCH_RESULT: $result")
        currentSearch.update { query }
        if(result is NetworkResponse.Success){
            val owners = result.body.items.map {
                it.owner
            }.toSet().map {
                OwnerEntity(
                    name=it.name,
                    avatarImageUrl = it.avatarImageUrl,
                    profileUrl = it.profileLink
                )
            }
            val repos = result.body.items.map {
                RepositoryEntity(
                    id = it.id,
                    name = it.name,
                    fullName = it.fullName,
                    description = it.description,
                    starsCount = it.starsCount,
                    updatedAt = it.lastUpdatedAt.toString(),
                    repositoryUrl = it.repositoryUrl,
                    forksCount = it.forksCount,
                    createdAt = it.createdAt.toString(),
                    ownerName = it.owner.name
                )
            }
            searchResultDao.insertDataFromSearchResult(
                searchQuery = query,
                repositories = repos,
                owners = owners
            )
        }

    }
}