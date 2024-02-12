package com.pottymotty.gitreposearch.data.api.api_services

import com.pottymotty.gitreposearch.data.api.call_response.NetworkResponse
import com.pottymotty.gitreposearch.data.api.dto.RepositorySearchResultDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GithubApiService {

    @Headers("accept: application/vnd.github+json")
    @GET("/search/repositories")
    suspend fun fetchRepositorySearchResult(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
        ) : NetworkResponse<RepositorySearchResultDto,String>
}