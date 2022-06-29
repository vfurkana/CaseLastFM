package com.vfurkana.caselastfm.data.service.remote.model

import com.google.gson.annotations.SerializedName

data class TopAlbumsAPIResponseModel(
    @SerializedName("topalbums") val topAlbums: TopAlbums,
    val message: String,
    val error: Long
)

data class TopAlbums(
    val album: List<TopAlbum>,
    @SerializedName("@attr") val attr: TopAlbumsAPIAttr
)

data class TopAlbum(
    val name: String,
    @SerializedName("playcount") val playCount: Long,
    val mbid: String? = null,
    val url: String,
    val artist: AlbumArtist,
    val image: List<Image>
)

data class AlbumArtist(
    val name: String,
    val mbid: String,
    val url: String
)

data class TopAlbumsAPIAttr(
    val artist: String,
    val page: String,
    val perPage: String,
    val totalPages: String,
    val total: String
)