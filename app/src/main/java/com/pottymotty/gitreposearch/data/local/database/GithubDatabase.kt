package com.pottymotty.gitreposearch.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pottymotty.gitreposearch.data.local.dao.SearchResultDao
import com.pottymotty.gitreposearch.data.local.entities.OwnerEntity
import com.pottymotty.gitreposearch.data.local.entities.RepositoryEntity
import com.pottymotty.gitreposearch.data.local.entities.SearchQueryEntity
import com.pottymotty.gitreposearch.data.local.entities.relations.SearchQueryRepositoryCrossRef

const val githubDatabaseName = "GITHUB_DB"
@Database(
    entities = [
        OwnerEntity::class,
        RepositoryEntity::class,
        SearchQueryEntity::class,
        SearchQueryRepositoryCrossRef::class
    ], version = 1
)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun searchDao() : SearchResultDao
}