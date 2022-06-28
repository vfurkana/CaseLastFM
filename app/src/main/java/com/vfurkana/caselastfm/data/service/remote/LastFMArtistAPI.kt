package com.vfurkana.caselastfm.data.service.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface LastFMArtistAPI {

    @GET("?method=artist.gettopalbums")
    suspend fun getTopAlbums(
        @Query("artist") artist: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): String
}