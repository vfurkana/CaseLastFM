package com.vfurkana.artistsearch.domain.di

import com.vfurkana.caselastfm.common.data.repository.LastFMRepository
import com.vfurkana.artistsearch.domain.mapper.ArtistDomainMapper
import com.vfurkana.artistsearch.domain.usecase.ArtistSearchUseCase
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
    fun provideArtistSearchUseCase(lastFMRepository: LastFMRepository, artistDomainMapper: ArtistDomainMapper): ArtistSearchUseCase {
        return ArtistSearchUseCase(lastFMRepository, artistDomainMapper)
    }
}