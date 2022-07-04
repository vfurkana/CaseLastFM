package com.vfurkana.caselastfm.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.vfurkana.caselastfm.data.repository.LastFMRepository
import com.vfurkana.caselastfm.domain.mapper.TopAlbumDomainMapper
import com.vfurkana.caselastfm.domain.model.TopAlbum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtistTopAlbumsUseCase @Inject constructor(val lastFMRepository: LastFMRepository, private val topAlbumsDomainMapper: TopAlbumDomainMapper) {

    suspend fun getTopAlbumsPaged(query: String?): Flow<PagingData<TopAlbum>> {
        return lastFMRepository.getArtistTopAlbumsPaged(query)
            .map {
                val artistAlbums = lastFMRepository.getArtistSavedAlbums(query)
                it.map {
                    topAlbumsDomainMapper.mapTopAlbumFromAPIResponse(it).apply {
                        isSaved = artistAlbums.any { it.albumName == name }
                    }
                }
            }
    }


    suspend fun saveAlbum(album: String, artist: String) {
        lastFMRepository.saveAlbum(album, artist)
    }

    suspend fun removeAlbum(album: String) {
        lastFMRepository.removeAlbum(album)
    }
}