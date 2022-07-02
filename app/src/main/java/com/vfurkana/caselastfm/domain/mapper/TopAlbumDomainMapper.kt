package com.vfurkana.caselastfm.domain.mapper

import com.vfurkana.caselastfm.data.service.remote.model.LastFMTopAlbumsAPIResponse
import com.vfurkana.caselastfm.domain.model.Image
import com.vfurkana.caselastfm.domain.model.TopAlbum

object TopAlbumDomainMapper {

    fun mapTopAlbumFromAPIResponse(albumApiResponse: LastFMTopAlbumsAPIResponse.TopAlbum): TopAlbum {
        return TopAlbum(
            albumApiResponse.name,
            albumApiResponse.url,
            albumApiResponse.artist.name,
            albumApiResponse.image.map {
                Image(
                    it.size?.let { Image.ImageSize.valueOf(it.name) },
                    it.url
                )
            }
        )
    }
}