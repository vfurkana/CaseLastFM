package com.vfurkana.caselastfm.domain.mapper

import com.vfurkana.caselastfm.data.service.remote.model.AlbumDetailAPIResponse
import com.vfurkana.caselastfm.domain.model.Album
import com.vfurkana.caselastfm.domain.model.Image
import com.vfurkana.caselastfm.domain.model.Track

object AlbumDetailDomainMapper {

    fun mapAlbumDetailFromAPIResponse(albumApiResponse: AlbumDetailAPIResponse): Album {
        return Album(
            albumApiResponse.name,
            albumApiResponse.artist,
            albumApiResponse.url,
            albumApiResponse.image.map {
                Image(
                    it.size?.let { Image.ImageSize.valueOf(it.name) },
                    it.url
                )
            },
            albumApiResponse.tracks?.track?.map {
                Track(
                    it.duration,
                    it.url,
                    it.name
                )
            }
        )
    }
}