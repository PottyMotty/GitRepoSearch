package com.pottymotty.gitreposearch.data.repositories.repo_search

import com.pottymotty.gitreposearch.data.api.NetworkDataSource
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
        //val result = networkDataSource.fetchRepositorySearchResults(query)
        //Timber.d("FETCH_RESULT: $result")
        currentSearch.update { query }

        searchResultDao.insertDataFromSearchResult(
            searchQuery = "tetris",
            repositories = listOf(
                RepositoryEntity(
                    id = 0,
                    name = "Tetris 1",
                    fullName = "fullNAme/Tetris 1",
                    description = "Tatris game",
                    starsCount = 3,
                    updatedAt = "time",
                    repositoryUrl = "url",
                    forksCount = 10,
                    createdAt = "time2",
                    ownerName = "potty"
                ),
                RepositoryEntity(
                    id = 1,
                    name = "Tetris 2",
                    fullName = "fullNAme/Tetris 2",
                    description = "Tatris game",
                    starsCount = 34,
                    updatedAt = "time",
                    repositoryUrl = "url",
                    forksCount = 103,
                    createdAt = "time2",
                    ownerName = "potty"
                )
            ),
            owners = listOf(
                OwnerEntity(
                    name = "potty", avatarImageUrl = "image", profileUrl = "url"
                )
            )
        )
    }
}