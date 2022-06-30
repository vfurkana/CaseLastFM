package com.vfurkana.caselastfm.data.service.remote.model

import com.google.gson.annotations.SerializedName

data class LastFMAlbumInfoAPIResponseModel(
    val album: AlbumDetailAPIResponse,
    val message: String,
    val error: Long
)

data class AlbumDetailAPIResponse(
    val artist: String,
    val mbid: String?,
    val tags: TagsApiResponse?,
    val playcount: String,
    val image: List<ImageAPIResponse>,
    val tracks: TracksApiResponse?,
    val url: String,
    val name: String,
    val listeners: String,
    val wiki: WikiApiResponse
)

data class TagsApiResponse(
    val tag: List<TagApiResponse>
)

data class TagApiResponse(
    val url: String,
    val name: String
)

data class TracksApiResponse(
    val track: List<TrackApiResponse>
)

data class TrackApiResponse(
    val streamable: StreamableApiResponse?,
    val duration: Long? = null,
    val url: String,
    val name: String,
    @SerializedName("@attr") val attr: AlbumInfoAttrApiResponse,
    val artist: TrackArtistApiResponse
)

data class TrackArtistApiResponse(
    val url: String,
    val name: String,
    val mbid: String?
)

data class AlbumInfoAttrApiResponse(
    val rank: Long
)

data class StreamableApiResponse(
    val fulltrack: String,
    @SerializedName("#text") val text: String
)

data class WikiApiResponse(
    val published: String,
    val summary: String,
    val content: String
)