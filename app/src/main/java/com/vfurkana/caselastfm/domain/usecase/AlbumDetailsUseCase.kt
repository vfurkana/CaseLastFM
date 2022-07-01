package com.vfurkana.caselastfm.domain.usecase

import com.vfurkana.caselastfm.data.repository.LastFMRepository
import com.vfurkana.caselastfm.domain.mapper.AlbumDetailDomainMapper
import com.vfurkana.caselastfm.domain.model.Album
import javax.inject.Inject

class AlbumDetailsUseCase @Inject constructor(val lastFMRepository: LastFMRepository, private val domainMapper: AlbumDetailDomainMapper) {

    suspend fun getAlbumDetails(album: String, artist: String): Album {
        return domainMapper.mapAlbumDetailFromAPIResponse(lastFMRepository.getAlbumDetail(album, artist))
    }
}