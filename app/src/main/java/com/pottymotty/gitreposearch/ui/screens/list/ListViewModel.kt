package com.pottymotty.gitreposearch.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.pottymotty.gitreposearch.data.repositories.repo_search.RepoSearchRepository
import com.pottymotty.gitreposearch.mapper.toModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

data class SearchResultState(
    val searchQuery: String,
)

class ListViewModel(
    initialSearchQuery: String,
    private val repoSearchRepository: RepoSearchRepository,
) : ViewModel() {

    private val _screenState = MutableStateFlow(
        SearchResultState(
            searchQuery = initialSearchQuery,
        )
    )
    val screenState = _screenState.asStateFlow()

    private val lastExecutedQuery = MutableStateFlow<String?>(initialSearchQuery)

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagedItems = lastExecutedQuery.filterNotNull().flatMapLatest { query ->
        repoSearchRepository.getPaginatedSearchResult(query,40,20).map { pagingData ->
            pagingData.map { it.toModel().repository }
        }
    }.cachedIn(viewModelScope)


    fun fetchSearchResults(searchQuery: String) {
        lastExecutedQuery.update {
            searchQuery
        }
    }

    fun setSearchQuery(newQuery: String) {
        _screenState.update {
            it.copy(searchQuery = newQuery)
        }
    }
}