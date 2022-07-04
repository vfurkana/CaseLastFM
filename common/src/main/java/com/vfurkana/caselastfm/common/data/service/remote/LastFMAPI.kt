package com.vfurkana.caselastfm.common.data.service.remote

import com.vfurkana.caselastfm.common.data.service.remote.model.LastFMAlbumInfoAPIResponseModel
import com.vfurkana.caselastfm.common.data.service.remote.model.LastFMSearchArtistAPIResponseModel
import com.vfurkana.caselastfm.common.data.service.remote.model.LastFMTopAlbumsAPIResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFMAPI {

    @GET("?method=artist.gettopalbums")
    suspend fun getTopAlbumsByArtist(
        @Query("artist") artist: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): LastFMTopAlbumsAPIResponse

    @GET("?method=artist.search")
    suspend fun searchArtist(
        @Query("artist") artist: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): LastFMSearchArtistAPIResponseModel

    @GET("?method=album.getinfo")
    suspend fun getAlbumInfo(
        @Query("album") album: String,
        @Query("artist") artist: String
    ): LastFMAlbumInfoAPIResponseModel
}