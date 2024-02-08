package com.pottymotty.gitreposearch.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pottymotty.gitreposearch.data.repositories.repo_search.RepoSearchRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repoSearchRepository: RepoSearchRepository) : ViewModel() {

    fun fetchSearch(){
        viewModelScope.launch {
            repoSearchRepository.fetchSearchResults("tetris")
        }
    }
}