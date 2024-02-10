package com.pottymotty.gitreposearch.model

data class GithubRepositoryWithOwner(
    val repository : GithubRepository,
    val owner: RepositoryOwner,
)