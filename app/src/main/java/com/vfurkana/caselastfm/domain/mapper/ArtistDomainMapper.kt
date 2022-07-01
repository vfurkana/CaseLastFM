package com.vfurkana.caselastfm.domain.mapper

import com.vfurkana.caselastfm.data.service.remote.model.ArtistApiResponse
import com.vfurkana.caselastfm.domain.model.Artist
import com.vfurkana.caselastfm.domain.model.Image

object ArtistDomainMapper {

    fun mapArtistFromAPIResponse(artistApiResponse: ArtistApiResponse): Artist {
        return Artist(
            artistApiResponse.name,
            artistApiResponse.image.map {
                Image(
                    it.size?.let { Image.ImageSize.valueOf(it.name) },
                    it.url
                )
            },
            artistApiResponse.listeners,
            artistApiResponse.url
        )
    }
}