package com.vfurkana.caselastfm.data.service.remote.model

import com.google.gson.annotations.SerializedName

data class TopAlbumsAPIResponseModel(
    val topalbums: TopAlbums,
    val message: String,
    val error: Long
)

data class TopAlbums(
    val album: List<Album>,
    @SerializedName("@attr") val attr: TopAlbumsAPIAttr
)

data class Album(
    val name: String,
    val playcount: Long,
    val mbid: String? = null,
    val url: String,
    val artist: ArtistClass,
    val image: List<Image>
)

data class ArtistClass(
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