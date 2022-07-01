package com.vfurkana.caselastfm.data.repository

import androidx.paging.*
import com.vfurkana.caselastfm.data.repository.mapper.ApiResponseToEntityMapper
import com.vfurkana.caselastfm.data.repository.mapper.EntityToApiResponseMapper
import com.vfurkana.caselastfm.data.repository.pagingsource.SearchArtistsPagingResource
import com.vfurkana.caselastfm.data.repository.pagingsource.TopAlbumsPagingResource
import com.vfurkana.caselastfm.data.service.local.dao.AlbumsAndArtistsDao
import com.vfurkana.caselastfm.data.service.remote.api.LastFMAPI
import com.vfurkana.caselastfm.data.service.remote.model.AlbumDetailAPIResponse
import com.vfurkana.caselastfm.data.service.remote.model.ArtistApiResponse
import com.vfurkana.caselastfm.data.service.remote.model.TopAlbumApiResponse
import com.vfurkana.caselastfm.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LastFMRepository @Inject constructor(
    val remote: LastFMAPI,
    val local: AlbumsAndArtistsDao,
    val localToRemoteMapper: EntityToApiResponseMapper,
    val remoteToLocalMapper: ApiResponseToEntityMapper,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
) {
    fun searchArtistPaged(artist: String?, inScope: CoroutineScope): Flow<PagingData<ArtistApiResponse>> {
        return Pager(
            PagingConfig(pageSize = 20, initialLoadSize = 20)
        ) {
            SearchArtistsPagingResource(artist, remote)
        }.flow.cachedIn(inScope).flowOn(ioDispatcher)
    }

    suspend fun getSavedAlbums(): Flow<List<AlbumDetailAPIResponse>> {
        return local.getSavedAlbums().flowOn(ioDispatcher).map { it.map { localToRemoteMapper.mapSavedAlbumEntityToApiResponse(it) } }

    }

    suspend fun saveAlbum(album: AlbumDetailAPIResponse) {
        return withContext(ioDispatcher) {
            local.insertSavedAlbum(remoteToLocalMapper.mapAlbumDetailToSavedAlbumEntity(album))
        }
    }

    fun getArtistTopAlbumsPaged(artist: String?, inScope: CoroutineScope): Flow<PagingData<TopAlbumApiResponse>> {
        return Pager(
            PagingConfig(pageSize = 20, initialLoadSize = 20)
        ) {
            TopAlbumsPagingResource(artist, remote)
        }.flow.cachedIn(inScope).flowOn(ioDispatcher)
    }
}
