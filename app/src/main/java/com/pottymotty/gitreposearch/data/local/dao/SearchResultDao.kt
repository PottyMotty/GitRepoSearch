package com.pottymotty.gitreposearch.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.pottymotty.gitreposearch.data.local.entities.OwnerEntity
import com.pottymotty.gitreposearch.data.local.entities.RepositoryEntity
import com.pottymotty.gitreposearch.data.local.entities.SearchQueryEntity
import com.pottymotty.gitreposearch.data.local.entities.relations.SearchQueryRepositoryCrossRef
import com.pottymotty.gitreposearch.data.local.entities.relations.SearchQueryWithRepositoryAndOwner
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchResultDao {
    @Upsert
    suspend fun upsertAllRepositories(repositories: List<RepositoryEntity>)

    @Upsert
    suspend fun upsertAllOwners(owners: List<OwnerEntity>)

    @Insert(onConflict = REPLACE)
    suspend fun insertSearchQuery(query: SearchQueryEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertSearchQueryRepositoryCrossRef(crossRef: SearchQueryRepositoryCrossRef)

    @Transaction
    suspend fun insertDataFromSearchResult(searchQuery: String,repositories: List<RepositoryEntity>, owners: List<OwnerEntity>){
        insertSearchQuery(SearchQueryEntity(searchQuery))
        upsertAllOwners(owners)
        upsertAllRepositories(repositories)
        repositories.forEach {
            insertSearchQueryRepositoryCrossRef(
                SearchQueryRepositoryCrossRef(
                    searchQuery = searchQuery,
                    repositoryId = it.id
                )
            )
        }
    }
    @Transaction
    @Query("SELECT * FROM search_query WHERE `query`= :searchQuery")
    fun getResultsForQuery(searchQuery: String) : Flow<SearchQueryWithRepositoryAndOwner>
}