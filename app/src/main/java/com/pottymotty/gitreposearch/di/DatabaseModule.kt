package com.pottymotty.gitreposearch.di

import androidx.room.Room
import com.pottymotty.gitreposearch.data.local.dao.SearchResultDao
import com.pottymotty.gitreposearch.data.local.database.GithubDatabase
import com.pottymotty.gitreposearch.data.local.database.githubDatabaseName
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module{
    single<GithubDatabase>{
        Room.databaseBuilder(androidContext(), GithubDatabase::class.java, githubDatabaseName)
            .fallbackToDestructiveMigration()
            .build()
    }

    single<SearchResultDao>{
        val db = get<GithubDatabase>()
        db.searchDao()
    }
}