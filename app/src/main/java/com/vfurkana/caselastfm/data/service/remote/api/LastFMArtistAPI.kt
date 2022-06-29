package com.vfurkana.caselastfm.data.service.remote.api

import com.vfurkana.caselastfm.data.service.remote.model.SearchAPIResponseModel
import com.vfurkana.caselastfm.data.service.remote.model.TopAlbumsAPIResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFMArtistAPI {

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
    ): SearchAPIResponseModel
}