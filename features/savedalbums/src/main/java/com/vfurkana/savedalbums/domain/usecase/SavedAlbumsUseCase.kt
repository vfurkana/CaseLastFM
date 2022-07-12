package com.vfurkana.savedalbums.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.vfurkana.caselastfm.common.data.repository.LastFMRepository
import com.vfurkana.savedalbums.domain.mapper.SavedAlbumsDomainMapper
import com.vfurkana.caselastfm.common.domain.model.Album
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SavedAlbumsUseCase @Inject constructor(val lastFMRepository: LastFMRepository, private val domainMapper: SavedAlbumsDomainMapper) {
    suspend fun getSavedAlbums(): Flow<PagingData<Album>> {
        return lastFMRepository.getSavedAlbums().map { it.map { domainMapper.mapAlbumDetailFromEntity(it) } }
    }

    suspend fun removeAlbum(album: String) {
        lastFMRepository.removeAlbum(album)
    }
}