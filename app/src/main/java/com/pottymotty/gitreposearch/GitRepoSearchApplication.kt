package com.pottymotty.gitreposearch

import android.app.Application
import com.pottymotty.gitreposearch.di.moshiModule
import com.pottymotty.gitreposearch.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GitRepoSearchApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            allowOverride(true)
            androidLogger(Level.ERROR)
            androidContext(this@GitRepoSearchApplication)
            modules(
                listOf(
                    networkModule,
                    moshiModule
                )
            )
        }
    }
}