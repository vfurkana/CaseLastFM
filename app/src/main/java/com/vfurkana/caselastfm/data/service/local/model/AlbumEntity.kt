package com.vfurkana.caselastfm.data.service.local.model

import androidx.room.*

@Entity(tableName = "albums")
@TypeConverters(LastFMTypeConverters::class)
data class AlbumEntity(
    @PrimaryKey val albumName: String,
    @ColumnInfo(name = "artist") val artist: String,
    @ColumnInfo(name = "images") val images: List<Image>,
    @ColumnInfo(name = "tracks") val tracks: List<Track>?
) {

    data class Track(
        val duration: Long? = null,
        val url: String,
        val name: String,
        val artistName: String
    )

    data class Image(
        val url: String,
        val size: Size?
    ){
        enum class Size {
            ExtraLarge,
            Large,
            Medium,
            Mega,
            Small;
        }
    }
}