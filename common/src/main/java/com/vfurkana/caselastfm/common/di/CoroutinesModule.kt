package com.vfurkana.caselastfm.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier


@Module
@InstallIn(ActivityRetainedComponent::class)
object CoroutinesModule {

    @Provides
    @MainDispatcher
    fun providesMainDispatcher() = Dispatchers.Main

    @Provides
    @DefaultDispatcher
    fun providesDefaultDispatcher() = Dispatchers.Default

    @Provides
    @IoDispatcher
    fun providesIoDispatcher() = Dispatchers.IO

}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher
