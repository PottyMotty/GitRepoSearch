package com.pottymotty.gitreposearch.di

import com.pottymotty.gitreposearch.data.serialization.InstantAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module
import java.time.Instant

val moshiModule = module {
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(Instant::class.java, InstantAdapter())
            .build()
    }
}