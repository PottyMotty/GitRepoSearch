package com.pottymotty.gitreposearch.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OwnerDto(
    @Json(name="login")val name: String,
    @Json(name="avatar_url")val avatarImageUrl: String,
    @Json(name="html_url")val profileLink: String,
)