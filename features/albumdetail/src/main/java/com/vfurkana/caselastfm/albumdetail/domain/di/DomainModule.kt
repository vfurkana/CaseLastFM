package com.vfurkana.caselastfm.albumdetail.domain.di

import com.vfurkana.caselastfm.albumdetail.domain.mapper.AlbumDetailDomainMapper
import com.vfurkana.caselastfm.albumdetail.domain.usecase.AlbumDetailsUseCase
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
    fun provideAlbumDetailMapper(): AlbumDetailDomainMapper {
        return AlbumDetailDomainMapper
    }


    @Provides
    @ViewModelScoped
    fun provideAlbumDetailUseCase(lastFMRepository: com.vfurkana.caselastfm.common.data.repository.LastFMRepository, albumDetailDomainMapper: AlbumDetailDomainMapper): AlbumDetailsUseCase {
        return AlbumDetailsUseCase(lastFMRepository, albumDetailDomainMapper)
    }

}