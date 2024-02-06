package com.pottymotty.gitreposearch.data.api.interceptors

import com.pottymotty.gitreposearch.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class GithubVersionInterceptor : Interceptor{

    companion object {
        private const val versionHeaderName: String = "X-GitHub-Api-Version"
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder().addHeader(versionHeaderName, BuildConfig.GITHUB_API_VERSION).build()
        return chain.proceed(request)
    }
}