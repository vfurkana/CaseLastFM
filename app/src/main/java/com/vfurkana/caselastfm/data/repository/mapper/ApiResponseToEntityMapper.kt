package com.vfurkana.caselastfm.data.repository.mapper

import com.vfurkana.caselastfm.data.service.local.model.*
import com.vfurkana.caselastfm.data.service.remote.model.AlbumDetail
import com.vfurkana.caselastfm.data.service.remote.model.Artist

object ApiResponseToEntityMapper {

    fun mapAlbumDetailToSavedAlbumEntity(albumDetail: AlbumDetail): SavedAlbumEntity {
        return SavedAlbumEntity(mapAlbumDetailToAlbumEntity(albumDetail))
    }

    fun mapAlbumDetailToAlbumEntity(albumDetail: AlbumDetail): AlbumEntity {
        return AlbumEntity(
            BaseAlbumEntity(
                albumDetail.name,
                albumDetail.playcount.toLong(),
                albumDetail.mbid,
                albumDetail.url,
                albumDetail.artist,
                albumDetail.image.map {
                    ImageEntity(
                        it.url,
                        it.size?.let { SizeEntity.valueOf(it.name) }
                    )
                }
            ),
            albumDetail.tags?.tag?.map {
                TagEntity(
                    it.url,
                    it.name
                )
            },
            albumDetail.tracks.track.map {
                TrackEntity(
                    it.streamable,
                    it.duration,
                    it.url,
                    it.name,
                    it.attr,
                    BaseArtistEntity(
                        it.artist.name,
                        it.artist.mbid,
                        it.artist.url
                    )
                )
            },
            albumDetail.listeners,
            WikiEntity(albumDetail.wiki.published, albumDetail.wiki.summary, albumDetail.wiki.content)
        )
    }

    fun mapArtistToEntity(artist: Artist): ArtistEntity {
        return ArtistEntity(
            BaseArtistEntity(
                artist.name,
                artist.mbid,
                artist.url
            ),
            artist.listeners,
            artist.streamable,
            artist.image.map {
                ImageEntity(it.url, it.size?.let { SizeEntity.valueOf(it.name) })
            }
        )
    }
}