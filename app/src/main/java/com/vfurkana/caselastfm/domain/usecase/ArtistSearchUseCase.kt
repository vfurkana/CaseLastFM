package com.vfurkana.caselastfm.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.vfurkana.caselastfm.domain.mapper.ArtistDomainMapper
import com.vfurkana.caselastfm.common.domain.model.Artist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtistSearchUseCase @Inject constructor(private val lastFMRepository: com.vfurkana.caselastfm.common.data.repository.LastFMRepository, private val domainMapper: ArtistDomainMapper) {

    suspend fun searchArtistsPaged(query: String?): Flow<PagingData<Artist>> {
        return lastFMRepository.searchArtistPaged(query).map { it.map { domainMapper.mapArtistFromAPIResponse(it) } }
    }
}