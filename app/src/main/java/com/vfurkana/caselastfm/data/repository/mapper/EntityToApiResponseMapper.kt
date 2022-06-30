package com.vfurkana.caselastfm.data.repository.mapper

import com.vfurkana.caselastfm.data.service.local.model.AlbumEntity
import com.vfurkana.caselastfm.data.service.local.model.ArtistEntity
import com.vfurkana.caselastfm.data.service.local.model.SavedAlbumEntity
import com.vfurkana.caselastfm.data.service.remote.model.*

object EntityToApiResponseMapper {

    fun mapSavedAlbumEntityToApiResponse(savedAlbumEntity: SavedAlbumEntity): AlbumDetailAPIResponse {
        return mapAlbumEntityToApiResponse(savedAlbumEntity.albumEntity)
    }


    fun mapAlbumEntityToApiResponse(albumEntity: AlbumEntity): AlbumDetailAPIResponse {
        return AlbumDetailAPIResponse(
            albumEntity.baseAlbumEntity.artist,
            albumEntity.baseAlbumEntity.mbid,
            albumEntity.tags?.map { TagApiResponse(it.url, it.name) }?.let { TagsApiResponse(it) },
            albumEntity.baseAlbumEntity.playcount.toString(),
            albumEntity.baseAlbumEntity.images.map { ImageAPIResponse(it.url, it.size?.let { SizeAPIResponse.valueOf(it.name) }) },
            albumEntity.tracks?.map {
                TrackApiResponse(
                    it.streamable?.let { StreamableApiResponse(it.fulltrack, it.text) },
                    it.duration,
                    it.url,
                    it.name,
                    AlbumInfoAttrApiResponse(it.attr.rank),
                    TrackArtistApiResponse(it.artist.url, it.artist.artistName, it.artist.mbid)
                )
            }?.let {
                TracksApiResponse(it)
            },
            albumEntity.baseAlbumEntity.url,
            albumEntity.baseAlbumEntity.albumName,
            albumEntity.listeners,
            WikiApiResponse(albumEntity.wiki.published, albumEntity.wiki.summary, albumEntity.wiki.content)
        )
    }

    fun mapArtistEntityToApiResponse(artistEntity: ArtistEntity): ArtistApiResponse {
        return ArtistApiResponse(
            artistEntity.baseArtistEntity.artistName,
            artistEntity.listeners,
            artistEntity.baseArtistEntity.mbid,
            artistEntity.baseArtistEntity.url,
            artistEntity.streamable,
            artistEntity.image.map {
                ImageAPIResponse(it.url, it.size?.let {
                    SizeAPIResponse.valueOf(it.name)
                })
            }
        )
    }
}