package com.pottymotty.gitreposearch.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pottymotty.gitreposearch.data.api.dto.RepositoryDto
import com.pottymotty.gitreposearch.model.toModel

@Entity(tableName = "repositories")
data class RepositoryEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "repository_id")
    val id: Long,
    val name: String,
    @ColumnInfo(name="full_name")
    val fullName: String,
    val description: String?,
    @ColumnInfo(name="stars_count")
    val starsCount: Int,
    @ColumnInfo(name="last_update_time")
    val updatedAt: String,
    val repositoryUrl: String,
    @ColumnInfo(name="forks_count")
    val forksCount: Int,
    @ColumnInfo(name="creation_time")
    val createdAt: String,
    @ColumnInfo(name="owner_name")
    val ownerName: String,
)

fun RepositoryDto.toEntity() : RepositoryEntity =
    RepositoryEntity(
        id = this.id,
        name = this.name,
        fullName = this.fullName,
        description = this.description,
        starsCount = this.starsCount,
        forksCount = this.forksCount,
        createdAt =this.createdAt,
        updatedAt =this.lastUpdatedAt,
        repositoryUrl = this.repositoryUrl,
        ownerName = this.owner.name
    )