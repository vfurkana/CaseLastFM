package com.vfurkana.caselastfm.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import com.vfurkana.artistsearch.navigation.ArtistSearchNavigations
import com.vfurkana.caselastfm.R
import com.vfurkana.caselastfm.albumdetail.view.AlbumDetailFragment
import com.vfurkana.caselastfm.common.domain.model.Album
import com.vfurkana.caselastfm.common.domain.model.Artist
import com.vfurkana.caselastfm.common.domain.model.TopAlbum
import com.vfurkana.savedalbums.navigation.SavedAlbumsNavigations
import com.vfurkana.topalbums.navigation.TopAlbumsNavigations
import com.vfurkana.topalbums.view.TopAlbumsFragment
import javax.inject.Inject

class NavigationImpl @Inject constructor(private val navigationController: NavController?) :
    SavedAlbumsNavigations,
    TopAlbumsNavigations,
    ArtistSearchNavigations {

    override fun navigateToDetail(album: Album) {
        navigationController?.navigate(R.id.fragmentAlbumDetail, bundleOf(AlbumDetailFragment.KEY_EXTRAS_ALBUM to album))
    }

    override fun navigateToDetail(album: TopAlbum) {
        navigationController?.navigate(
            R.id.fragmentAlbumDetail,
            bundleOf(AlbumDetailFragment.KEY_EXTRAS_TOP_ALBUM to album),
            NavOptions.Builder().setRestoreState(true).build()
        )
    }

    override fun navigateToSearch() {
        navigationController?.navigate(R.id.fragmentArtistSearch)
    }

    override fun navigateToTopAlbums(artist: Artist) {
        navigationController?.navigate(
            R.id.fragmentTopAlbums, bundleOf(TopAlbumsFragment.KEY_EXTRAS_ARTIST to artist), NavOptions.Builder().setRestoreState(true).build()
        )
    }
}