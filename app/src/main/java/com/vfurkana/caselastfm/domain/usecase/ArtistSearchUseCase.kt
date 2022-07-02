package com.vfurkana.caselastfm.domain.usecase

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.map
import com.vfurkana.caselastfm.data.repository.LastFMRepository
import com.vfurkana.caselastfm.domain.mapper.ArtistDomainMapper
import com.vfurkana.caselastfm.domain.model.Artist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtistSearchUseCase @Inject constructor(private val lastFMRepository: LastFMRepository, private val domainMapper: ArtistDomainMapper) {

    suspend fun searchArtistsPaged(query: String?): Flow<PagingData<Artist>> {
        return lastFMRepository.searchArtistPaged(query).map { it.map { domainMapper.mapArtistFromAPIResponse(it) } }
    }
}