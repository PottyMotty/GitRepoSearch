package com.pottymotty.gitreposearch.data.api.api_services

import com.pottymotty.gitreposearch.data.api.call_response.NetworkResponse
import retrofit2.http.GET

interface GithubApiService {

    @GET
    suspend fun fetchRepositorySearchResult() : NetworkResponse<Unit,String>
}