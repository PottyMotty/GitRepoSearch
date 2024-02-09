package com.pottymotty.gitreposearch.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pottymotty.gitreposearch.data.repositories.repo_search.RepoSearchRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(private val repoSearchRepository: RepoSearchRepository) : ViewModel() {

    fun fetchSearch(){
        viewModelScope.launch {
            repoSearchRepository.fetchSearchResults("monkey")
        }
    }

    init {
        repoSearchRepository.currentSearchResult.onEach {
            Timber.d("DATA: $it")
        }.launchIn(viewModelScope)
    }
}