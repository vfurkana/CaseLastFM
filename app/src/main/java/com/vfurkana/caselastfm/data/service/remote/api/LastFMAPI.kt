package com.vfurkana.caselastfm.data.service.remote.api

import com.vfurkana.caselastfm.data.service.remote.model.*
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