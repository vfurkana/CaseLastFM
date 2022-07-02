package com.vfurkana.caselastfm.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.vfurkana.caselastfm.data.repository.LastFMRepository
import com.vfurkana.caselastfm.domain.mapper.AlbumDetailDomainMapper
import com.vfurkana.caselastfm.domain.model.Album
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SavedAlbumsUseCase @Inject constructor(val lastFMRepository: LastFMRepository, private val domainMapper: AlbumDetailDomainMapper) {
    suspend fun getSavedAlbums(): Flow<PagingData<Album>> {
        return lastFMRepository.getSavedAlbums().map { it.map { domainMapper.mapAlbumDetailFromEntity(it) } }
    }
}