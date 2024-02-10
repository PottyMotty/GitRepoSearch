package com.pottymotty.gitreposearch.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "search_query")
data class SearchQueryEntity(
    @PrimaryKey(autoGenerate = false)
    val query: String,
    val timestamp: String,
    @ColumnInfo(name = "total_result_count")
    val totalResultCount: Int,
)