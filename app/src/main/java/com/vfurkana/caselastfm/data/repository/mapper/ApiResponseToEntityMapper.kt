package com.vfurkana.caselastfm.data.repository.mapper

import com.vfurkana.caselastfm.data.service.local.model.*
import com.vfurkana.caselastfm.data.service.remote.model.AlbumDetailAPIResponse
import com.vfurkana.caselastfm.data.service.remote.model.ArtistApiResponse

object ApiResponseToEntityMapper {

    fun mapAlbumDetailToSavedAlbumEntity(albumDetail: AlbumDetailAPIResponse): SavedAlbumEntity {
        return SavedAlbumEntity(mapAlbumDetailToAlbumEntity(albumDetail))
    }

    fun mapAlbumDetailToAlbumEntity(albumDetail: AlbumDetailAPIResponse): AlbumEntity {
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
                    StreamableEntity(it.streamable.fulltrack, it.streamable.text),
                    it.duration,
                    it.url,
                    it.name,
                    AlbumInfoAttrEntity(it.attr.rank),
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

    fun mapArtistToEntity(artist: ArtistApiResponse): ArtistEntity {
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