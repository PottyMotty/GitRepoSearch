package com.pottymotty.gitreposearch.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.Instant

@JsonClass(generateAdapter = true)
data class RepositoryDto(
    val id: Long,
    val name: String,
    @Json(name= "full_name")val fullName : String,
    val description: String?,
    @Json(name="stargazers_count")val starsCount: Int,
    @Json(name="updated_at")val lastUpdatedAt : String,
    val owner: OwnerDto,
    @Json(name="html_url")val repositoryUrl : String,
    @Json(name="forks_count") val forksCount: Int,
    @Json(name="created_at") val createdAt: String,
)