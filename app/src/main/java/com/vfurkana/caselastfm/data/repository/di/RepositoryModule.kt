package com.vfurkana.caselastfm.data.repository.di

import com.vfurkana.caselastfm.data.repository.mapper.ApiResponseToEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRemoteToLocalMapper(): ApiResponseToEntityMapper {
        return ApiResponseToEntityMapper
    }
}