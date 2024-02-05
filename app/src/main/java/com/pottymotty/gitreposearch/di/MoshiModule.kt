package com.pottymotty.gitreposearch.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module

val moshiModule = module{
    single {
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }
}