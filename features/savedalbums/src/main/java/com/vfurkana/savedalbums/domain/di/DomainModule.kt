package com.vfurkana.savedalbums.domain.di

import com.vfurkana.caselastfm.common.data.repository.LastFMRepository
import com.vfurkana.savedalbums.domain.mapper.SavedAlbumsDomainMapper
import com.vfurkana.savedalbums.domain.usecase.SavedAlbumsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    @ViewModelScoped
    fun provideSavedAlbumsDomainMapper(): SavedAlbumsDomainMapper {
        return SavedAlbumsDomainMapper
    }

    @Provides
    @ViewModelScoped
    fun provideSavedAlbumsUseCase(lastFMRepository: LastFMRepository, savedAlbumsDomainMapper: SavedAlbumsDomainMapper): SavedAlbumsUseCase {
        return SavedAlbumsUseCase(lastFMRepository, savedAlbumsDomainMapper)
    }
}