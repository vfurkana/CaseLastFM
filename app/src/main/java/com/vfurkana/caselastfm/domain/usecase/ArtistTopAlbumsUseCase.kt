package com.vfurkana.caselastfm.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.vfurkana.caselastfm.data.repository.LastFMRepository
import com.vfurkana.caselastfm.domain.mapper.TopAlbumDomainMapper
import com.vfurkana.caselastfm.domain.model.TopAlbum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtistTopAlbumsUseCase @Inject constructor(val lastFMRepository: LastFMRepository, private val domainMapper: TopAlbumDomainMapper) {

    suspend fun getTopAlbumsPaged(query: String?): Flow<PagingData<TopAlbum>> {
        return lastFMRepository.getArtistTopAlbumsPaged(query).map { it.map { domainMapper.mapTopAlbumFromAPIResponse(it) } }
    }
}