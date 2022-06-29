package com.vfurkana.caselastfm.data.service.remote.interceptor

import com.vfurkana.caselastfm.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

object LastFMSecretInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url
        val newRequest = request
            .newBuilder()
            .url(
                url.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.API_KEY)
                    .build()
            )
            .build()
        return chain.proceed(newRequest)
    }
}