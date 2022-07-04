package com.vfurkana.savedalbums.domain.mapper

import com.vfurkana.caselastfm.common.data.service.local.model.AlbumEntity
import com.vfurkana.caselastfm.common.domain.model.Album
import com.vfurkana.caselastfm.common.domain.model.Image
import com.vfurkana.caselastfm.common.domain.model.Track

object SavedAlbumsDomainMapper {

    fun mapAlbumDetailFromEntity(albumEntity: AlbumEntity): Album {
        return Album(
            albumEntity.albumName,
            albumEntity.artist,
            albumEntity.images.map {
                Image(
                    it.size?.let { Image.ImageSize.valueOf(it.name) },
                    it.url
                )
            },
            albumEntity.tracks?.map {
                Track(it.duration, it.url, it.name, it.artistName)
            }
        )
    }

    fun mapAlbumDetailToEntity(album: Album): AlbumEntity {
        return AlbumEntity(
            album.name,
            album.artistName,
            album.image.map {
                AlbumEntity.Image(
                    it.url,
                    it.size?.let { AlbumEntity.Image.Size.valueOf(it.name) },
                )
            },
            album.tracks?.map {
                AlbumEntity.Track(it.duration, it.url, it.name, it.artistName)
            }
        )
    }
}