package com.pottymotty.gitreposearch.data.local.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.pottymotty.gitreposearch.data.local.entities.OwnerEntity
import com.pottymotty.gitreposearch.data.local.entities.RepositoryEntity

data class RepositoryWithOwner(
    @Embedded val repository: RepositoryEntity,
    @Relation(
        parentColumn = "owner_name",
        entityColumn = "name"
    )
    val owner: OwnerEntity
)