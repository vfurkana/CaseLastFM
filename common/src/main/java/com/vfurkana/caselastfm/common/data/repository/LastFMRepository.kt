package com.vfurkana.caselastfm.common.data.repository

import android.util.Log
import androidx.paging.*
import com.vfurkana.caselastfm.common.data.service.local.dao.AlbumsDao
import com.vfurkana.caselastfm.common.data.service.local.model.AlbumEntity
import com.vfurkana.caselastfm.common.data.service.remote.LastFMAPI
import com.vfurkana.caselastfm.common.data.service.remote.model.LastFMAlbumInfoAPIResponseModel
import com.vfurkana.caselastfm.common.data.service.remote.model.LastFMSearchArtistAPIResponseModel
import com.vfurkana.caselastfm.common.data.service.remote.model.LastFMTopAlbumsAPIResponse
import com.vfurkana.caselastfm.common.di.IoDispatcher
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class LastFMRepository @Inject constructor(
    private val remote: LastFMAPI,
    private val local: AlbumsDao,
    private val remoteToLocalMapper: com.vfurkana.caselastfm.common.data.repository.mapper.ApiResponseToEntityMapper,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
) {
    fun searchArtistPaged(artist: String?): Flow<PagingData<LastFMSearchArtistAPIResponseModel.Artist>> {
        return if (artist.isNullOrEmpty()) flow { emit(PagingData.empty()) } else {
            Pager(PagingConfig(pageSize = 20, initialLoadSize = 20)) {
                com.vfurkana.caselastfm.common.data.repository.pagingsource.SearchArtistsPagingResource(artist, remote)
            }.flow.flowOn(ioDispatcher)
        }
    }

    fun getArtistTopAlbumsPaged(artist: String?): Flow<PagingData<LastFMTopAlbumsAPIResponse.TopAlbum>> {
        return if (artist.isNullOrEmpty()) {
            flow { emit(PagingData.empty()) }
        } else {
            Pager(PagingConfig(pageSize = 20, initialLoadSize = 20)) {
                com.vfurkana.caselastfm.common.data.repository.pagingsource.TopAlbumsPagingResource(artist, remote)
            }.flow.flowOn(ioDispatcher)
        }
    }

    suspend fun getArtistSavedAlbums(artist: String?): List<AlbumEntity> {
        return withContext(ioDispatcher) {
            artist?.let { local.getAlbumsByArtist(artist) } ?: listOf()
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
                convertedAlbum
            }
            emit(trueAlbum)
        }.flowOn(ioDispatcher)
    }

    suspend fun getAlbumSaveStatus(album: String): Flow<Boolean> {
        return local.getAlbumFlow(album).map { it.isNotEmpty() }.flowOn(ioDispatcher)
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

    suspend fun saveAlbum(album: AlbumEntity) {
        return withContext(ioDispatcher) {
            local.insertAlbum(album)
        }
    }

    suspend fun saveAlbum(album: String, artist: String) {
        withContext(ioDispatcher) {
            val remoteAlbum = remote.getAlbumInfo(album, artist)
            val convertedAlbum = remoteToLocalMapper.mapAlbumResponseToAlbumEntity(remoteAlbum.album)
            local.insertAlbum(convertedAlbum)
        }
    }

    suspend fun removeAlbum(album: String) {
        withContext(ioDispatcher) {
            Log.i("furkooo", "rows removed: ${local.removeAlbum(album)}")
        }
    }

    object AlbumNotFoundInLocalDataException : Exception()
}
