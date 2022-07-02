package com.vfurkana.caselastfm.domain.usecase

import android.util.Log
import com.vfurkana.caselastfm.data.repository.LastFMRepository
import com.vfurkana.caselastfm.domain.mapper.AlbumDetailDomainMapper
import com.vfurkana.caselastfm.domain.model.Album
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AlbumDetailsUseCase @Inject constructor(val lastFMRepository: LastFMRepository, private val domainMapper: AlbumDetailDomainMapper) {

    suspend fun getAlbumDetails(album: String, artist: String): Flow<Album> {
        return lastFMRepository.getAlbumDetail(album, artist).map { domainMapper.mapAlbumDetailFromEntity(it) }
    }
}