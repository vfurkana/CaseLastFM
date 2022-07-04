package com.vfurkana.savedalbums.navigation

import com.vfurkana.caselastfm.common.domain.model.Album

interface SavedAlbumsNavigations {
    fun navigateToDetail(album: Album)
    fun navigateToSearch()
}