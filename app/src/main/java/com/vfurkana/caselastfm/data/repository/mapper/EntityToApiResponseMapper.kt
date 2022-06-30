package com.vfurkana.caselastfm.data.repository.mapper

import com.vfurkana.caselastfm.data.service.local.model.AlbumEntity
import com.vfurkana.caselastfm.data.service.local.model.ArtistEntity
import com.vfurkana.caselastfm.data.service.local.model.SavedAlbumEntity
import com.vfurkana.caselastfm.data.service.remote.model.*

object EntityToApiResponseMapper {

    fun mapSavedAlbumEntityToApiResponse(savedAlbumEntity: SavedAlbumEntity): AlbumDetail {
        return mapAlbumEntityToApiResponse(savedAlbumEntity.albumEntity)
    }


    fun mapAlbumEntityToApiResponse(albumEntity: AlbumEntity): AlbumDetail {
        return AlbumDetail(
            albumEntity.baseAlbumEntity.artist,
            albumEntity.baseAlbumEntity.mbid,
            albumEntity.tags?.map { Tag(it.url, it.name) }?.let { Tags(it) },
            albumEntity.baseAlbumEntity.playcount.toString(),
            albumEntity.baseAlbumEntity.images.map { Image(it.url, it.size?.let { Size.valueOf(it.name) }) },
            Tracks(albumEntity.tracks.map {
                Track(
                    it.streamable,
                    it.duration,
                    it.url,
                    it.name,
                    it.attr,
                    TrackArtist(it.artist.url, it.artist.artistName, it.artist.mbid)
                )
            }),
            albumEntity.baseAlbumEntity.url,
            albumEntity.baseAlbumEntity.albumName,
            albumEntity.listeners,
            Wiki(albumEntity.wiki.published, albumEntity.wiki.summary, albumEntity.wiki.content)
        )
    }

    fun mapArtistEntityToApiResponse(artistEntity: ArtistEntity): Artist {
        return Artist(
            artistEntity.baseArtistEntity.artistName,
            artistEntity.listeners,
            artistEntity.baseArtistEntity.mbid,
            artistEntity.baseArtistEntity.url,
            artistEntity.streamable,
            artistEntity.image.map {
                Image(it.url, it.size?.let {
                    Size.valueOf(it.name)
                })
            }
        )
    }
}