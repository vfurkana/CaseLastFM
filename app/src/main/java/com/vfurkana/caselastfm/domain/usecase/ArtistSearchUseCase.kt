package com.vfurkana.caselastfm.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.vfurkana.caselastfm.data.repository.LastFMRepository
import com.vfurkana.caselastfm.domain.mapper.ArtistDomainMapper
import com.vfurkana.caselastfm.domain.model.Artist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtistSearchUseCase @Inject constructor(private val lastFMRepository: LastFMRepository, private val domainMapper: ArtistDomainMapper) {

    fun searchArtistsPaged(query: String?, inScope: CoroutineScope): Flow<PagingData<Artist>> {
        return lastFMRepository.searchArtistPaged(query, inScope).map { it.map { domainMapper.mapArtistFromAPIResponse(it) } }
    }
}