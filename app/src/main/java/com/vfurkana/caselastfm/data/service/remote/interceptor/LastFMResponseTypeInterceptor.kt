package com.vfurkana.caselastfm.data.service.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

object LastFMResponseTypeInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url
        val newRequest = request
            .newBuilder()
            .url(
                url.newBuilder()
                    .addQueryParameter("format", "json")
                    .build()
            )
            .build()
        return chain.proceed(newRequest)
    }
}