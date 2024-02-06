package com.pottymotty.gitreposearch.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepositorySearchResultDto(
    @Json(name = "total_count") val totalCount: Int,
    val items: List<RepositoryDto>,
)