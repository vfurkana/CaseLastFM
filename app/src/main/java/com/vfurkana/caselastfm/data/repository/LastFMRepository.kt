package com.vfurkana.caselastfm.data.repository

import androidx.paging.*
import com.vfurkana.caselastfm.data.repository.mapper.ApiResponseToEntityMapper
import com.vfurkana.caselastfm.data.repository.mapper.EntityToApiResponseMapper
import com.vfurkana.caselastfm.data.repository.pagingsource.SearchArtistsPagingResource
import com.vfurkana.caselastfm.data.repository.pagingsource.TopAlbumsPagingResource
import com.vfurkana.caselastfm.data.service.local.dao.AlbumsDao
import com.vfurkana.caselastfm.data.service.local.model.AlbumEntity
import com.vfurkana.caselastfm.data.service.remote.api.LastFMAPI
import com.vfurkana.caselastfm.data.service.remote.model.LastFMAlbumInfoAPIResponseModel
import com.vfurkana.caselastfm.data.service.remote.model.LastFMSearchArtistAPIResponseModel
import com.vfurkana.caselastfm.data.service.remote.model.LastFMTopAlbumsAPIResponse
import com.vfurkana.caselastfm.di.IoDispatcher
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class LastFMRepository @Inject constructor(
    private val remote: LastFMAPI,
    private val local: AlbumsDao,
    private val remoteToLocalMapper: ApiResponseToEntityMapper,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
) {
    fun searchArtistPaged(artist: String?, inScope: CoroutineScope): Flow<PagingData<LastFMSearchArtistAPIResponseModel.Artist>> {
        return if (artist.isNullOrEmpty()) flow { PagingData.empty<LastFMSearchArtistAPIResponseModel.Artist>() } else
            Pager(PagingConfig(pageSize = 20, initialLoadSize = 20)) {
                SearchArtistsPagingResource(artist, remote)
            }.flow.cachedIn(inScope).flowOn(ioDispatcher)
    }

    fun getArtistTopAlbumsPaged(artist: String?, inScope: CoroutineScope): Flow<PagingData<LastFMTopAlbumsAPIResponse.TopAlbum>> {
        return if (artist.isNullOrEmpty()) {
            flow { PagingData.empty<LastFMTopAlbumsAPIResponse.TopAlbum>() }
        } else {
            Pager(PagingConfig(pageSize = 20, initialLoadSize = 20)) {
                TopAlbumsPagingResource(artist, remote)
            }.flow.cachedIn(inScope).flowOn(ioDispatcher)
        }
    }

    suspend fun getAlbumDetail(album: String, artist: String): AlbumEntity {
        return withContext(ioDispatcher) {
            supervisorScope {
                runCatching {
                    local.getAlbum(album) ?: throw AlbumNotFoundInLocalDataException
                }.recoverCatching {
                    remoteToLocalMapper.mapAlbumResponseToAlbumEntity(remote.getAlbumInfo(album, artist).album).also { local.insertAlbum(it) }
                }.getOrThrow()
            }
        }
    }

    fun getSavedAlbums(): Flow<List<AlbumEntity>> {
        return local.getAllAlbums().flowOn(ioDispatcher)
    }

    suspend fun saveAlbum(album: LastFMAlbumInfoAPIResponseModel.AlbumDetail) {
        return withContext(ioDispatcher) {
            local.insertAlbum(remoteToLocalMapper.mapAlbumResponseToAlbumEntity(album))
        }
    }

    object AlbumNotFoundInLocalDataException : Exception()
}
