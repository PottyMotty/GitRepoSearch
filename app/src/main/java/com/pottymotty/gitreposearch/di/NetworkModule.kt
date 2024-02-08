package com.pottymotty.gitreposearch.di

import com.pottymotty.gitreposearch.BuildConfig
import com.pottymotty.gitreposearch.data.api.NetworkDataSource
import com.pottymotty.gitreposearch.data.api.NetworkDataSourceImpl
import com.pottymotty.gitreposearch.data.api.api_services.GithubApiService
import com.pottymotty.gitreposearch.data.api.call_response.NetworkResponseAdapterFactory
import com.pottymotty.gitreposearch.data.api.interceptors.DefaultHeadersInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single {
        DefaultHeadersInterceptor()
    }
    single {
        OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            addInterceptor(get<DefaultHeadersInterceptor>())
        }.build()
    }
    single {
        val moshiConverter: MoshiConverterFactory = get()
        val httpClient: OkHttpClient = get()
        Retrofit.Builder().baseUrl(BuildConfig.BASE_API_URL)
            .addConverterFactory(
                moshiConverter
            ).addCallAdapterFactory(
                NetworkResponseAdapterFactory()
            ).client(httpClient).build()
    }
    single {
        MoshiConverterFactory.create(get())
    }
    single {
        val retrofit = get<Retrofit>()
        retrofit.create(GithubApiService::class.java)
    }
    single<NetworkDataSource> {
        NetworkDataSourceImpl(
            githubApi = get()
        )
    }
}