package com.vfurkana.artistsearch.navigation

import com.vfurkana.caselastfm.common.domain.model.Artist

interface ArtistSearchNavigations {
    fun navigateToTopAlbums(artistName: Artist)
}