package com.vfurkana.caselastfm.data.service.local.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.vfurkana.caselastfm.data.service.remote.model.AlbumInfoAttr
import com.vfurkana.caselastfm.data.service.remote.model.Streamable
import com.vfurkana.caselastfm.data.service.remote.model.TrackArtist


@Entity(tableName = "albums", primaryKeys = ["albumName"])
@TypeConverters(LastFMTypeConverters::class)
data class AlbumEntity(
    @Embedded val baseAlbumEntity: BaseAlbumEntity,
    @ColumnInfo(name = "tags") val tags: List<TagEntity>?,
    @ColumnInfo(name = "tracks") val tracks: List<TrackEntity>?,
    @ColumnInfo(name = "listeners") val listeners: String?
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
    val streamable: Streamable,
    val duration: Long? = null,
    val url: String,
    val name: String,
    @SerializedName("@attr") val attr: AlbumInfoAttr,
    val artist: TrackArtist
)