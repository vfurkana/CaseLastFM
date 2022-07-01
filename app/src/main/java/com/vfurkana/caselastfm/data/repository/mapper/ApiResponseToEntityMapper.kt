package com.vfurkana.caselastfm.data.repository.mapper

import com.vfurkana.caselastfm.data.service.local.model.*
import com.vfurkana.caselastfm.data.service.remote.model.LastFMAlbumInfoAPIResponseModel

object ApiResponseToEntityMapper {

    fun mapAlbumResponseToAlbumEntity(albumDetail: LastFMAlbumInfoAPIResponseModel.AlbumDetail): AlbumEntity {
        return AlbumEntity(
            albumDetail.name,
            albumDetail.artist,
            albumDetail.image.map {
                AlbumEntity.Image(
                    it.url,
                    it.size?.let { AlbumEntity.Size.valueOf(it.name) }
                )
            },
            albumDetail.tracks?.track?.map {
                AlbumEntity.Track(
                    it.duration,
                    it.url,
                    it.name,
                    it.artist.name,
                )
            },
        )
    }
}