package com.vfurkana.caselastfm.domain.di

import com.vfurkana.caselastfm.data.repository.LastFMRepository
import com.vfurkana.caselastfm.domain.mapper.AlbumDetailDomainMapper
import com.vfurkana.caselastfm.domain.mapper.ArtistDomainMapper
import com.vfurkana.caselastfm.domain.mapper.TopAlbumDomainMapper
import com.vfurkana.caselastfm.domain.usecase.AlbumDetailsUseCase
import com.vfurkana.caselastfm.domain.usecase.ArtistSearchUseCase
import com.vfurkana.caselastfm.domain.usecase.ArtistTopAlbumsUseCase
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
    fun provideArtistMapper(): ArtistDomainMapper {
        return ArtistDomainMapper
    }

    @Provides
    @ViewModelScoped
    fun provideTopAlbumMapper(): TopAlbumDomainMapper {
        return TopAlbumDomainMapper
    }

    @Provides
    @ViewModelScoped
    fun provideAlbumDetailMapper(): AlbumDetailDomainMapper {
        return AlbumDetailDomainMapper
    }

    @Provides
    @ViewModelScoped
    fun provideArtistSearchUseCase(lastFMRepository: LastFMRepository, artistDomainMapper: ArtistDomainMapper): ArtistSearchUseCase {
        return ArtistSearchUseCase(lastFMRepository, artistDomainMapper)
    }

    @Provides
    @ViewModelScoped
    fun provideArtistTopAlbumsUseCase(lastFMRepository: LastFMRepository, topAlbumDomainMapper: TopAlbumDomainMapper): ArtistTopAlbumsUseCase {
        return ArtistTopAlbumsUseCase(lastFMRepository, topAlbumDomainMapper)
    }

    @Provides
    @ViewModelScoped
    fun provideAlbumDetailUseCase(lastFMRepository: LastFMRepository, albumDetailDomainMapper: AlbumDetailDomainMapper): AlbumDetailsUseCase {
        return AlbumDetailsUseCase(lastFMRepository, albumDetailDomainMapper)
    }
}