package com.pottymotty.gitreposearch.data.local.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.pottymotty.gitreposearch.data.local.entities.RepositoryEntity
import com.pottymotty.gitreposearch.data.local.entities.SearchQueryEntity
import com.pottymotty.gitreposearch.model.GithubRepositoryWithOwner

data class SearchQueryWithRepositoryAndOwner(
    @Embedded val query: SearchQueryEntity,
    @Relation(
        entity = RepositoryEntity::class,
        parentColumn = "query",
        entityColumn = "repository_id",
        associateBy = Junction(SearchQueryRepositoryCrossRef::class)
    )
    val repositoriesWithOwner: List<RepositoryWithOwner>
)

