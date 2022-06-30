package com.vfurkana.caselastfm.data.service.local.model

import androidx.room.*


@Entity(tableName = "savedAlbums", primaryKeys = ["albumName"])
@TypeConverters(LastFMTypeConverters::class)
data class SavedAlbumEntity(
    @Embedded val albumEntity: AlbumEntity,
)

@Entity(tableName = "albums", primaryKeys = ["albumName"])
@TypeConverters(LastFMTypeConverters::class)
data class AlbumEntity(
    @Embedded val baseAlbumEntity: BaseAlbumEntity,
    @ColumnInfo(name = "tags") val tags: List<TagEntity>?,
    @ColumnInfo(name = "tracks") val tracks: List<TrackEntity>?,
    @ColumnInfo(name = "listeners") val listeners: String,
    @ColumnInfo(name = "wiki") val wiki: WikiEntity,
)

@TypeConverters(LastFMTypeConverters::class)
@Entity(tableName = "basealbums")
data class BaseAlbumEntity(
    @PrimaryKey val albumName: String,
    @ColumnInfo(name = "playcount") val playcount: Long,
    @ColumnInfo(name = "mbid") val mbid: String?,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "artist") val artist: String,
    @ColumnInfo(name = "images") val images: List<ImageEntity>
)


data class TagEntity(
    val url: String,
    val name: String
)

data class TrackEntity(
    val streamable: StreamableEntity?,
    val duration: Long? = null,
    val url: String,
    val name: String,
    val attr: AlbumInfoAttrEntity,
    val artist: BaseArtistEntity
)

data class StreamableEntity(
    val fulltrack: String,
    val text: String
)

data class AlbumInfoAttrEntity(
    val rank: Long
)

data class WikiEntity(
    val published: String,
    val summary: String,
    val content: String
)