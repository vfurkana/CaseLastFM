package com.vfurkana.caselastfm.domain.usecase

import com.vfurkana.caselastfm.data.repository.LastFMRepository
import com.vfurkana.caselastfm.data.service.remote.model.LastFMAlbumInfoAPIResponseModel
import com.vfurkana.caselastfm.domain.mapper.AlbumDetailDomainMapper
import com.vfurkana.caselastfm.domain.model.Album
import com.vfurkana.caselastfm.view.common.ViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlbumDetailsUseCase @Inject constructor(val lastFMRepository: LastFMRepository, private val domainMapper: AlbumDetailDomainMapper) {

    suspend fun getAlbumDetails(album: String, artist: String): Flow<Album> {
        return lastFMRepository.getAlbumDetail(album, artist).map { domainMapper.mapAlbumDetailFromEntity(it) }
    }

    suspend fun getSaveStatus(album: String): Flow<Boolean> {
        return lastFMRepository.getAlbumSaveStatus(album)
    }

    suspend fun saveAlbum(album: Album) {
        lastFMRepository.saveAlbum(domainMapper.mapAlbumDetailToEntity(album))
    }

    suspend fun removeAlbum(album: Album) {
        lastFMRepository.removeAlbum(album.name)
    }
}