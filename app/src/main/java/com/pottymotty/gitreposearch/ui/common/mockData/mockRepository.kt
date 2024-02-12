package com.pottymotty.gitreposearch.ui.common.mockData

import com.pottymotty.gitreposearch.model.GithubRepository
import com.pottymotty.gitreposearch.util.createInstant

val mockRepository = GithubRepository(
    id = 0,
    name = "Test repo",
    fullName = "pottymotty/Test repo",
    description = "This is a test repository.",
    starsCount = 100,
    forksCount = 3,
    createdAt = createInstant(2024,1,23),
    lastUpdatedAt = createInstant(2024,2,2),
    repositoryUrl = "www.google.com"

)