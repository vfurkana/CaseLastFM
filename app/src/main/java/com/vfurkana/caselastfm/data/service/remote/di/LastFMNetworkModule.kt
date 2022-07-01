package com.vfurkana.caselastfm.data.service.remote.di

import com.vfurkana.caselastfm.BuildConfig
import com.vfurkana.caselastfm.data.service.remote.api.LastFMAPI
import com.vfurkana.caselastfm.data.service.remote.interceptor.LastFMResponseTypeInterceptor
import com.vfurkana.caselastfm.data.service.remote.interceptor.LastFMSecretInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LastFMNetworkModule {

    private const val BASE_URL = "https://ws.audioscrobbler.com/2.0/"
    private const val TIMEOUT_SECONDS = 30L

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class LastFMApiKey

    @Provides
    @Singleton
    @LastFMApiKey
    fun provideLastFMApiKey(): String {
        return BuildConfig.LAST_FM_API_KEY
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named("loggingInterceptor") loggingInterceptor: HttpLoggingInterceptor,
        @Named("lastFMSecretInterceptor") secretInterceptor: Interceptor,
        @Named("lastFMResponseTypeInterceptor") responseTypeInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(secretInterceptor)
            .addInterceptor(responseTypeInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .callTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideLastFMArtistService(retrofit: Retrofit): LastFMAPI {
        return retrofit.create(LastFMAPI::class.java)
    }

    @Provides
    @Singleton
    @Named("loggingInterceptor")
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    @Named("lastFMSecretInterceptor")
    fun provideLastFMSecretInterceptor(@LastFMApiKey apiKey: String): Interceptor {
        return LastFMSecretInterceptor(apiKey)
    }

    @Provides
    @Singleton
    @Named("lastFMResponseTypeInterceptor")
    fun provideLastFMResponseTypeInterceptor(): Interceptor {
        return LastFMResponseTypeInterceptor
    }

}