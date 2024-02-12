package com.pottymotty.gitreposearch.model

import com.pottymotty.gitreposearch.data.api.dto.RepositoryDto
import com.pottymotty.gitreposearch.data.local.entities.RepositoryEntity
import java.time.Instant

data class GithubRepository(
    val id: Long,
    val name: String,
    val fullName: String,
    val description: String?,
    val starsCount: Int,
    val forksCount: Int,
    val createdAt: Instant,
    val lastUpdatedAt: Instant,
    val repositoryUrl: String,
)

