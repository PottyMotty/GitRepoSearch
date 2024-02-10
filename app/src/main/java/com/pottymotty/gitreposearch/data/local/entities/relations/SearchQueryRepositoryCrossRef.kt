package com.pottymotty.gitreposearch.data.local.entities.relations

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "search_query_and_repo_cross_ref",primaryKeys = ["query","repository_id"])
data class SearchQueryRepositoryCrossRef(
    @ColumnInfo("query")
    val searchQuery: String,
    @ColumnInfo("repository_id")
    val repositoryId: Long,
)