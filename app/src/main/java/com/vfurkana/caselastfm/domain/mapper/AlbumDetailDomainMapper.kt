package com.vfurkana.caselastfm.domain.mapper

import com.vfurkana.caselastfm.data.service.local.model.AlbumEntity
import com.vfurkana.caselastfm.domain.model.Album
import com.vfurkana.caselastfm.domain.model.Image
import com.vfurkana.caselastfm.domain.model.Track

object AlbumDetailDomainMapper {

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
                Track(it.duration, it.url, it.name)
            }
        )
    }
}