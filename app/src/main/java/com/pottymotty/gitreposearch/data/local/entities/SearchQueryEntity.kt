package com.pottymotty.gitreposearch.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "search_query")
data class SearchQueryEntity(
    @PrimaryKey(autoGenerate = false)
    val query: String,
)