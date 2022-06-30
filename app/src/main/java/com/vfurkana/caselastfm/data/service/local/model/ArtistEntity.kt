package com.vfurkana.caselastfm.data.service.local.model

import androidx.room.*

@Entity(tableName = "artists", primaryKeys = ["artistName"])
@TypeConverters(LastFMTypeConverters::class)
data class ArtistEntity(
    @Embedded val baseArtistEntity: BaseArtistEntity,
    @ColumnInfo(name = "listeners") val listeners: String,
    @ColumnInfo(name = "streamable") val streamable: String?,
    @ColumnInfo(name = "images") val image: List<ImageEntity>
)

data class BaseArtistEntity(
    @PrimaryKey val artistName: String,
    @ColumnInfo(name = "mbid") val mbid: String?,
    @ColumnInfo(name = "url") val url: String,
)
