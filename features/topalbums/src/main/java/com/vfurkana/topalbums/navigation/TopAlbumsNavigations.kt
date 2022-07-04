package com.vfurkana.topalbums.navigation

import com.vfurkana.caselastfm.common.domain.model.TopAlbum

interface TopAlbumsNavigations {
    fun navigateToDetail(album: TopAlbum)
}