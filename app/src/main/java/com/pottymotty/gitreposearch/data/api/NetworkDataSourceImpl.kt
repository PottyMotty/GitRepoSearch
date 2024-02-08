package com.pottymotty.gitreposearch.data.api

import com.pottymotty.gitreposearch.data.api.api_services.GithubApiService
import com.pottymotty.gitreposearch.data.api.call_response.NetworkResponse
import com.pottymotty.gitreposearch.data.api.dto.RepositorySearchResultDto

class NetworkDataSourceImpl(private val githubApi : GithubApiService) : NetworkDataSource {
    override suspend fun fetchRepositorySearchResults(query: String): NetworkResponse<RepositorySearchResultDto, String> {
        return githubApi.fetchRepositorySearchResult(query)
    }
}