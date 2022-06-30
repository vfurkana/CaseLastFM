package com.vfurkana.caselastfm.data.repository

import com.vfurkana.caselastfm.data.repository.mapper.ApiResponseToEntityMapper
import com.vfurkana.caselastfm.data.repository.mapper.EntityToApiResponseMapper
import com.vfurkana.caselastfm.data.service.local.dao.AlbumsAndArtistsDao
import com.vfurkana.caselastfm.data.service.remote.api.LastFMAPI
import com.vfurkana.caselastfm.data.service.remote.model.AlbumDetailAPIResponse
import com.vfurkana.caselastfm.data.service.remote.model.ArtistApiResponse
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
    val local: AlbumsAndArtistsDao,
    val localToRemoteMapper: EntityToApiResponseMapper,
    val remoteToLocalMapper: ApiResponseToEntityMapper,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
) {

    suspend fun getSavedAlbums(): List<AlbumDetailAPIResponse> {
        return withContext(ioDispatcher) {
            local.getSavedAlbums().map { localToRemoteMapper.mapSavedAlbumEntityToApiResponse(it) }
        }
    }

    suspend fun saveAlbum(album: AlbumDetailAPIResponse) {
        return withContext(ioDispatcher) {
            local.insertSavedAlbum(remoteToLocalMapper.mapAlbumDetailToSavedAlbumEntity(album))
        }
    }

    suspend fun searchArtist(artist: String): Flow<List<ArtistApiResponse>> {
        return flow { emit(remote.searchArtist(artist).results.artistMatches.artists) }
            .flowOn(ioDispatcher)
            .onEach {
                local.insertAllArtists(it.map { remoteToLocalMapper.mapArtistToEntity(it) })
            }
    }

    suspend fun getArtistTopAlbums(artist: String): Flow<List<TopAlbum>> {
        return flow { emit(remote.getTopAlbumsByArtist(artist).topAlbums.album) }.flowOn(ioDispatcher)
    }

    suspend fun getArtistAlbum(artist: String, album: String): AlbumDetailAPIResponse {
        return withContext(ioDispatcher) {
            remote.getAlbumInfo(album, artist).album.also {
                local.insertAlbum(remoteToLocalMapper.mapAlbumDetailToAlbumEntity(it))
            }
        }
    }
}
