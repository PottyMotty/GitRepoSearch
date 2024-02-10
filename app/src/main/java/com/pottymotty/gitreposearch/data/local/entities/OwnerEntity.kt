package com.pottymotty.gitreposearch.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pottymotty.gitreposearch.data.api.dto.OwnerDto

@Entity(tableName = "owners")
data class OwnerEntity(
    @PrimaryKey
    val name: String,
    @ColumnInfo(name="avatar_image")
    val avatarImageUrl: String,
    @ColumnInfo(name="profile_url")
    val profileUrl: String,
)

fun OwnerDto.toEntity() : OwnerEntity {
    return OwnerEntity(
        name = this.name,
        avatarImageUrl = this.avatarImageUrl,
        profileUrl = this.profileLink
    )
}