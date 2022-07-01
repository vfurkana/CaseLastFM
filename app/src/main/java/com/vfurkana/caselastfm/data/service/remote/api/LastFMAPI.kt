package com.vfurkana.caselastfm.data.service.remote.api

import com.vfurkana.caselastfm.data.service.remote.model.LastFMAlbumInfoAPIResponseModel
import com.vfurkana.caselastfm.data.service.remote.model.SearchArtistAPIResponseModel
import com.vfurkana.caselastfm.data.service.remote.model.TopAlbumsAPIResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFMAPI {

    @GET("?method=artist.gettopalbums")
    suspend fun getTopAlbumsByArtist(
        @Query("artist") artist: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): TopAlbumsAPIResponseModel

    @GET("?method=artist.gettopalbums")
    suspend fun getTopAlbumsByMBID(
        @Query("mbid") mbid: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): TopAlbumsAPIResponseModel

    @GET("?method=artist.search")
    suspend fun searchArtist(
        @Query("artist") artist: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): SearchArtistAPIResponseModel

    @GET("?method=album.getinfo")
    suspend fun getAlbumInfo(
        @Query("album") album: String,
        @Query("artist") artist: String
    ): LastFMAlbumInfoAPIResponseModel
}