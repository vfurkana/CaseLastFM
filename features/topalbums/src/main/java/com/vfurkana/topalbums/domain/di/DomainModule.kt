package com.vfurkana.topalbums.domain.di

import com.vfurkana.caselastfm.common.data.repository.LastFMRepository
import com.vfurkana.topalbums.domain.mapper.TopAlbumDomainMapper
import com.vfurkana.topalbums.domain.usecase.ArtistTopAlbumsUseCase
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
    fun provideTopAlbumMapper(): TopAlbumDomainMapper {
        return TopAlbumDomainMapper
    }

    @Provides
    @ViewModelScoped
    fun provideArtistTopAlbumsUseCase(lastFMRepository: LastFMRepository, topAlbumDomainMapper: TopAlbumDomainMapper): ArtistTopAlbumsUseCase {
        return ArtistTopAlbumsUseCase(lastFMRepository, topAlbumDomainMapper)
    }
}