package com.pottymotty.gitreposearch.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pottymotty.gitreposearch.data.repositories.repo_search.RepoSearchRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class DetailViewModel(
    private val repoSearchRepository: RepoSearchRepository,
    private val repositoryId: Long,
) : ViewModel() {

    val detail = repoSearchRepository
        .getDetailItemForRepository(repositoryId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}