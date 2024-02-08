package com.pottymotty.gitreposearch.model

import com.pottymotty.gitreposearch.data.api.dto.RepositoryDto
import java.time.Instant

data class GithubRepository(
    val name: String,
    val fullName: String,
    val description: String,
    val starsCount: Int,
    val forksCount: Int,
    val createdAt: Instant,
    val lastUpdatedAt: Instant,
    val repositoryUrl: String,
    val owner: RepositoryOwner
)

fun RepositoryDto.toModel() : GithubRepository =
    GithubRepository(
        name = this.name,
        fullName = this.fullName,
        description = this.description,
        starsCount = this.starsCount,
        forksCount = this.forksCount,
        createdAt =this.createdAt,
        lastUpdatedAt =this.lastUpdatedAt,
        repositoryUrl = this.repositoryUrl,
        owner = this.owner.toModel()

    )