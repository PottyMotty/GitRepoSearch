package com.pottymotty.gitreposearch.model

import com.pottymotty.gitreposearch.data.api.dto.RepositorySearchResultDto

data class RepositorySearchResult(
    val totalCount: Int,
    val items: List<GithubRepository>
)

fun RepositorySearchResultDto.toModel() : RepositorySearchResult =
    RepositorySearchResult(
        totalCount = this.totalCount,
        items = this.items.map { it.toModel() }
    )