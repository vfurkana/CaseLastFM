package com.vfurkana.caselastfm.data.service.local.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.vfurkana.caselastfm.data.service.remote.model.Image

@Entity(tableName = "artists", primaryKeys = ["artistName"])
data class ArtistEntity(
    @Embedded val baseArtistEntity: BaseArtistEntity,
    @ColumnInfo(name = "listeners") val listeners: String?,
    @ColumnInfo(name = "streamable") val streamable: String?
)

data class BaseArtistEntity(
    @PrimaryKey val artistName: String,
    @ColumnInfo(name = "mbid") val mbid: String?,
    @ColumnInfo(name = "url") val url: String,
)
