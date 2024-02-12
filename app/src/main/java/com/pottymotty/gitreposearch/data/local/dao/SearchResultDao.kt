package com.pottymotty.gitreposearch.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.pottymotty.gitreposearch.data.local.entities.OwnerEntity
import com.pottymotty.gitreposearch.data.local.entities.RepositoryEntity
import com.pottymotty.gitreposearch.data.local.entities.SearchQueryEntity
import com.pottymotty.gitreposearch.data.local.entities.relations.RepositoryWithOwner
import com.pottymotty.gitreposearch.data.local.entities.relations.SearchQueryRepositoryCrossRef
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
    suspend fun insertSearchQueryRepositoryCrossRefAll(crossRef: List<SearchQueryRepositoryCrossRef>)

    @Transaction
    suspend fun insertDataFromSearchResult(
        searchQuery: SearchQueryEntity,
        repositories: List<RepositoryEntity>,
        owners: List<OwnerEntity>
    ) {
        insertSearchQuery(searchQuery)
        upsertAllOwners(owners)
        upsertAllRepositories(repositories)
        insertSearchQueryRepositoryCrossRefAll(
            repositories.map {
                SearchQueryRepositoryCrossRef(
                    searchQuery = searchQuery.query,
                    repositoryId = it.id
                )
            }
        )
    }

    @Query("DELETE FROM search_query where `query` = :searchQuery")
    suspend fun deleteQuery(searchQuery: String)

    @Query("DELETE FROM owners where name IN (:deleteNames)")
    suspend fun deleteOwners(deleteNames: List<String>)

    @Query("DELETE FROM repositories where `repository_id` IN (:deleteIDs)")
    suspend fun deleteRepos(deleteIDs: List<Long>)

    @Query("DELETE FROM search_query_and_repo_cross_ref where `query` = :searchQuery")
    suspend fun deleteCrossRef(searchQuery: String)

    @Query("""
        SELECT DISTINCT ref1.repository_id 
        FROM search_query_and_repo_cross_ref ref1 
        WHERE `query` = :searchQuery
        AND NOT EXISTS (
            SELECT 1
            FROM search_query_and_repo_cross_ref ref2
            WHERE ref2.repository_id = ref1.repository_id
            AND ref2.`query` <> :searchQuery
        )
    """)
    suspend fun getDeletableRepos(searchQuery: String): List<Long>

    @Query("""
        SELECT DISTINCT o.name
        FROM owners o
        LEFT JOIN repositories r ON o.name = r.owner_name 
        AND r.repository_id NOT IN (:deletableRepos)
        WHERE r.repository_id IS NULL;
    """)
    suspend fun getDeletableOwners(deletableRepos: List<Long>): List<String>

    @Transaction
    suspend fun deleteQueryData(searchQuery: String) {
        val getRepos = getDeletableRepos(searchQuery)
        val getOwners = getDeletableOwners(getRepos)
        deleteOwners(getOwners)
        deleteRepos(getRepos)
        deleteCrossRef(searchQuery)
        deleteQuery(searchQuery)
    }

    @Query("SELECT next_key FROM search_query WHERE `query` = :searchQuery")
    suspend fun getQueryNextKey(searchQuery: String): Int?

    @Transaction
    @Query("""
        SELECT repository.*
        FROM repositories repository
        JOIN search_query_and_repo_cross_ref query_repository ON repository.repository_id = query_repository.repository_id
        WHERE query_repository.`query` = :searchQuery
        ORDER BY query_repository.id ASC
    """)
    fun getResultsForQuery(searchQuery: String): PagingSource<Int, RepositoryWithOwner>


    @Query("SELECT * FROM repositories WHERE repository_id = :repositoryId")
    fun getRepository(repositoryId: Long) : Flow<RepositoryWithOwner>

}

