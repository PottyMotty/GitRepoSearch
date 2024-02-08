package com.pottymotty.gitreposearch.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OwnerEntity(
    @PrimaryKey
    val name: String,
    @ColumnInfo(name="avatar_image")
    val avatarImageUrl: String,
    @ColumnInfo(name="profile_url")
    val profileUrl: String,
)