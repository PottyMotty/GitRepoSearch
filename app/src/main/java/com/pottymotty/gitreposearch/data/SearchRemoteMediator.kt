package com.pottymotty.gitreposearch.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.pottymotty.gitreposearch.data.api.NetworkDataSource
import com.pottymotty.gitreposearch.data.api.call_response.NetworkResponse
import com.pottymotty.gitreposearch.data.local.database.GithubDatabase
import com.pottymotty.gitreposearch.data.local.entities.SearchQueryEntity
import com.pottymotty.gitreposearch.data.local.entities.relations.RepositoryWithOwner
import com.pottymotty.gitreposearch.data.local.entities.toEntity
import com.pottymotty.gitreposearch.data.util.handleResult
import com.pottymotty.gitreposearch.util.FuncResult
import java.time.Instant

@OptIn(ExperimentalPagingApi::class)
class SearchRemoteMediator(
    private val query: String,
    private val database: GithubDatabase,
    private val networkDataSource: NetworkDataSource,
) : RemoteMediator<Int, RepositoryWithOwner>() {

    companion object {
        private val linkRegex ="""page=(\d+).*?rel="([^"]+)"""".toRegex()
    }

    private val searchDao = database.searchDao()

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, RepositoryWithOwner>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                database.searchDao().getQueryNextKey(query) ?: return MediatorResult.Success(true)
            }
        }
        val networkCallResult =
            networkDataSource.fetchRepositorySearchResults(query, page, state.config.pageSize)
        //Questionable solution for getting the key for the next page
        val nextKey = (networkCallResult as? NetworkResponse.Success)?.let {result->
                result.headers["link"]?.map { it.split(",") }?.flatten()?.map {
                    val matchResult = linkRegex.find(it)
                    matchResult?.let {
                        val pageNumber = matchResult.groupValues[1].toInt()
                        val relString = matchResult.groupValues[2]
                        pageNumber to relString
                    }
                }?.find {
                    it?.second == "next"
                }?.first
            }
        val handledRes = networkCallResult.handleResult(
            onSuccess = { searchResultDto ->
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        searchDao.deleteQueryData(query)
                    }

                    val queryResultEntity = SearchQueryEntity(
                        query = query,
                        timestamp = Instant.now().toString(),
                        totalResultCount = searchResultDto.totalCount,
                        nextKey = nextKey
                    )
                    val ownersEntities = searchResultDto.items.map {
                        it.owner
                    }.toSet().map {
                        it.toEntity()
                    }
                    val reposEntities = searchResultDto.items.map {
                        it.toEntity()
                    }
                    searchDao.insertDataFromSearchResult(
                        searchQuery = queryResultEntity,
                        repositories = reposEntities,
                        owners = ownersEntities
                    )
                }
            },
        )
        return when (handledRes) {
            is FuncResult.Error -> MediatorResult.Error(handledRes.exception ?: Exception("Unknown failure"))
            is FuncResult.Success -> MediatorResult.Success(endOfPaginationReached = nextKey==null)
        }
    }

}