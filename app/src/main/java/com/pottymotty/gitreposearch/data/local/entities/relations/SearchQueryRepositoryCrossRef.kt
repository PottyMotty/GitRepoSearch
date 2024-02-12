package com.pottymotty.gitreposearch.data.local.entities.relations

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_query_and_repo_cross_ref")
data class SearchQueryRepositoryCrossRef(
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    @ColumnInfo("query")
    val searchQuery: String,
    @ColumnInfo("repository_id")
    val repositoryId: Long,
)