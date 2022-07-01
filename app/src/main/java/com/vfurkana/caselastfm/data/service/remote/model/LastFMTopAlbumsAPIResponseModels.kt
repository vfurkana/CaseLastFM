package com.vfurkana.caselastfm.data.service.remote.model

import com.google.gson.annotations.SerializedName

data class TopAlbumsAPIResponseModel(
    @SerializedName("topalbums") val topAlbums: TopAlbumsApiResponse,
    val message: String,
    val error: Long
)

data class TopAlbumsApiResponse(
    val album: List<TopAlbumApiResponse>,
    @SerializedName("@attr") val attr: TopAlbumsAPIAttrResponse
)

data class TopAlbumApiResponse(
    val name: String,
    @SerializedName("playcount") val playCount: Long,
    val mbid: String? = null,
    val url: String,
    val artist: AlbumArtistApiResponse,
    val image: List<ImageAPIResponse>
)

data class AlbumArtistApiResponse(
    val name: String,
    val mbid: String,
    val url: String
)

data class TopAlbumsAPIAttrResponse(
    val artist: String,
    val page: String,
    val perPage: String,
    val totalPages: String,
    val total: String
)