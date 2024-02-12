package com.pottymotty.gitreposearch.data.api

import com.pottymotty.gitreposearch.data.api.call_response.NetworkResponse
import com.pottymotty.gitreposearch.data.api.dto.RepositorySearchResultDto

interface NetworkDataSource {
    suspend fun fetchRepositorySearchResults(query : String,page: Int,pageSize: Int) : NetworkResponse<RepositorySearchResultDto, String>
}