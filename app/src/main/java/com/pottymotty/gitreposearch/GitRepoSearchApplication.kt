package com.pottymotty.gitreposearch

import android.app.Application
import com.pottymotty.gitreposearch.di.databaseModule
import com.pottymotty.gitreposearch.di.moshiModule
import com.pottymotty.gitreposearch.di.networkModule
import com.pottymotty.gitreposearch.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber.*
import timber.log.Timber.Forest.plant


class GitRepoSearchApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            plant(DebugTree())
        }
        startKoin {
            allowOverride(true)
            androidLogger(Level.ERROR)
            androidContext(this@GitRepoSearchApplication)
            modules(
                listOf(
                    networkModule,
                    moshiModule,
                    searchModule,
                    databaseModule
                )
            )
        }
    }
}