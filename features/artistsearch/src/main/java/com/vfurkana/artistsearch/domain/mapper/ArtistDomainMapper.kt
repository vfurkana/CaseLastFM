package com.vfurkana.artistsearch.domain.mapper

import com.vfurkana.caselastfm.common.data.service.remote.model.LastFMSearchArtistAPIResponseModel
import com.vfurkana.caselastfm.common.domain.model.Artist
import com.vfurkana.caselastfm.common.domain.model.Image

object ArtistDomainMapper {

    fun mapArtistFromAPIResponse(artistApiResponse: LastFMSearchArtistAPIResponseModel.Artist): Artist {
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