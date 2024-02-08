package com.pottymotty.gitreposearch.model

import com.pottymotty.gitreposearch.data.api.dto.OwnerDto

data class RepositoryOwner(
    val avatarImageUrl: String,
    val name: String,
    val linkToProfile: String,
)

fun OwnerDto.toModel() : RepositoryOwner =
    RepositoryOwner(
        avatarImageUrl = this.avatarImageUrl,
        name = this.name,
        linkToProfile = this.profileLink
    )