package com.vfurkana.caselastfm.data.repository

import com.vfurkana.caselastfm.data.service.local.dao.AlbumsDao
import com.vfurkana.caselastfm.data.repository.mapper.ApiResponseToEntityMapper
import com.vfurkana.caselastfm.data.repository.mapper.EntityToApiResponseMapper
import com.vfurkana.caselastfm.data.service.remote.api.LastFMAPI
import com.vfurkana.caselastfm.data.service.remote.model.AlbumDetail
import com.vfurkana.caselastfm.data.service.remote.model.Artist
import com.vfurkana.caselastfm.data.service.remote.model.TopAlbum
import com.vfurkana.caselastfm.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LastFMRepository @Inject constructor(
    val remote: LastFMAPI,
    val local: AlbumsDao,
    val localToRemoteMapper: EntityToApiResponseMapper,
    val remoteToLocalMapper: ApiResponseToEntityMapper,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
) {

    suspend fun getSavedAlbums(): List<AlbumDetail> {
        return withContext(ioDispatcher) {
            local.getSavedAlbums().map { localToRemoteMapper.mapSavedAlbumEntityToApiResponse(it) }
        }
    }

    suspend fun saveAlbum(album: AlbumDetail) {
        return withContext(ioDispatcher) {
            local.insertSavedAlbum(remoteToLocalMapper.mapAlbumDetailToSavedAlbumEntity(album))
        }
    }

    suspend fun searchArtist(artist: String): Flow<List<Artist>> {
        return flow { emit(remote.searchArtist(artist).results.artistMatches.artists) }
            .flowOn(ioDispatcher)
            .onEach {
                local.insertAllArtists(it.map { remoteToLocalMapper.mapArtistToEntity(it) })
            }
    }

    suspend fun getArtistTopAlbums(artist: String): Flow<List<TopAlbum>> {
        return flow { emit(remote.getTopAlbumsByArtist(artist).topAlbums.album) }.flowOn(ioDispatcher)
    }

    suspend fun getArtistAlbum(artist: String, album: String): AlbumDetail {
        return withContext(ioDispatcher) {
            remote.getAlbumInfo(album, artist).album.also {
                local.insertAlbum(remoteToLocalMapper.mapAlbumDetailToAlbumEntity(it))
            }
        }
    }
}
