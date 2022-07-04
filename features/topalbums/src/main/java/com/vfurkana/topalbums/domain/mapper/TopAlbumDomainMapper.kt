package com.vfurkana.topalbums.domain.mapper

import com.vfurkana.caselastfm.common.domain.model.Image
import com.vfurkana.caselastfm.common.domain.model.TopAlbum

object TopAlbumDomainMapper {

    fun mapTopAlbumFromAPIResponse(albumApiResponse: com.vfurkana.caselastfm.common.data.service.remote.model.LastFMTopAlbumsAPIResponse.TopAlbum): TopAlbum {
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