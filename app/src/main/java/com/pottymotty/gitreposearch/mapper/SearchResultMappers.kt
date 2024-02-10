package com.pottymotty.gitreposearch.mapper

import com.pottymotty.gitreposearch.data.local.entities.OwnerEntity
import com.pottymotty.gitreposearch.data.local.entities.RepositoryEntity
import com.pottymotty.gitreposearch.data.local.entities.relations.RepositoryWithOwner
import com.pottymotty.gitreposearch.data.local.entities.relations.SearchQueryWithRepositoryAndOwner
import com.pottymotty.gitreposearch.model.GithubRepository
import com.pottymotty.gitreposearch.model.GithubRepositoryWithOwner
import com.pottymotty.gitreposearch.model.RepositoryOwner
import java.time.Instant

fun RepositoryWithOwner.toModel() : GithubRepositoryWithOwner {
    return GithubRepositoryWithOwner(
        repository = this.repository.toModel(),
        owner = this.owner.toModel()
    )
}

fun SearchQueryWithRepositoryAndOwner.toModel() : List<GithubRepositoryWithOwner>{
    return this.repositoriesWithOwner.map { it.toModel() }
}

fun OwnerEntity.toModel() : RepositoryOwner  =
    RepositoryOwner(
        avatarImageUrl = this.avatarImageUrl,
        name = this.name,
        linkToProfile = this.profileUrl
    )

fun RepositoryEntity.toModel() : GithubRepository  =
    GithubRepository(
        name = this.name,
        fullName = this.fullName,
        description = this.description,
        starsCount = this.starsCount,
        forksCount =this.forksCount,
        createdAt = Instant.parse(this.createdAt),
        lastUpdatedAt =Instant.parse(this.updatedAt),
        repositoryUrl = this.repositoryUrl
    )
