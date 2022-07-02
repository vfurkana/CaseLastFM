package com.vfurkana.caselastfm.data.repository

import android.util.Log
import androidx.paging.*
import com.vfurkana.caselastfm.data.repository.mapper.ApiResponseToEntityMapper
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
    fun searchArtistPaged(artist: String?): Flow<PagingData<LastFMSearchArtistAPIResponseModel.Artist>> {
        return if (artist.isNullOrEmpty()) flow { emit(PagingData.empty()) } else {
            Pager(PagingConfig(pageSize = 20, initialLoadSize = 20)) {
                SearchArtistsPagingResource(artist, remote)
            }.flow.flowOn(ioDispatcher)
        }
    }

    fun getArtistTopAlbumsPaged(artist: String?): Flow<PagingData<LastFMTopAlbumsAPIResponse.TopAlbum>> {
        return if (artist.isNullOrEmpty()) {
            flow { emit(PagingData.empty()) }
        } else {
            Pager(PagingConfig(pageSize = 20, initialLoadSize = 20)) {
                TopAlbumsPagingResource(artist, remote)
            }.flow.flowOn(ioDispatcher)
        }
    }

    suspend fun getAlbumDetail(album: String, artist: String): Flow<AlbumEntity> {
        return flow {
            val localAlbum = local.getAlbum(album)
            val trueAlbum = if (localAlbum != null) {
                localAlbum
            } else {
                val remoteAlbum = remote.getAlbumInfo(album, artist).album
                val convertedAlbum = remoteToLocalMapper.mapAlbumResponseToAlbumEntity(remoteAlbum)
                local.insertAlbum(convertedAlbum)
                convertedAlbum
            }
            emit(trueAlbum)
        }.flowOn(ioDispatcher)
    }

    fun getSavedAlbums(): Flow<PagingData<AlbumEntity>> {
        return Pager(PagingConfig(pageSize = 20, initialLoadSize = 20)) {
            local.getAllAlbums()
        }.flow.flowOn(ioDispatcher)
    }

    suspend fun saveAlbum(album: LastFMAlbumInfoAPIResponseModel.AlbumDetail) {
        return withContext(ioDispatcher) {
            local.insertAlbum(remoteToLocalMapper.mapAlbumResponseToAlbumEntity(album))
        }
    }

    object AlbumNotFoundInLocalDataException : Exception()
}
