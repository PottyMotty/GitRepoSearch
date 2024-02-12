package com.pottymotty.gitreposearch.model

import com.pottymotty.gitreposearch.data.api.dto.RepositorySearchResultDto

data class RepositorySearchResult(
    val totalCount: Int,
    val searchQuery: String,
    val items: List<GithubRepositoryWithOwner>
) {
    val remainingItemsCount
        get() = totalCount - items.size
}
