package com.vfurkana.caselastfm.data.service.remote.model

import com.google.gson.annotations.SerializedName

data class LastFMAlbumInfoAPIResponseModel(
    val album: AlbumDetail,
    val message: String,
    val error: Long
)

data class AlbumDetail(
    val artist: String,
    val mbid: String,
    val tags: Tags,
    val playcount: String,
    val image: List<Image>,
    val tracks: Tracks,
    val url: String,
    val name: String,
    val listeners: String,
    val wiki: Wiki
)

data class Tags(
    val tag: List<Tag>
)

data class Tag(
    val url: String,
    val name: String
)

data class Tracks(
    val track: List<Track>
)

data class Track(
    val streamable: Streamable,
    val duration: Long? = null,
    val url: String,
    val name: String,
    @SerializedName("@attr") val attr: AlbumInfoAttr,
    val artist: TrackArtist
)

data class TrackArtist(
    val url: String,
    val name: String,
    val mbid: String
)

data class AlbumInfoAttr(
    val rank: Long
)

data class Streamable(
    val fulltrack: String,
    @SerializedName("#text") val text: String
)

data class Wiki(
    val published: String,
    val summary: String,
    val content: String
)