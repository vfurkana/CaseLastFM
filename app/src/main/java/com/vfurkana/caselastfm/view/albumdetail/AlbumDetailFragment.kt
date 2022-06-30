package com.vfurkana.caselastfm.view.albumdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.vfurkana.caselastfm.R
import com.vfurkana.caselastfm.data.repository.LastFMRepository
import com.vfurkana.caselastfm.domain.model.Album
import com.vfurkana.caselastfm.domain.model.Artist
import com.vfurkana.caselastfm.domain.model.Image
import com.vfurkana.caselastfm.domain.model.Track
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlbumDetailFragment : Fragment() {
    @Inject lateinit var repository: LastFMRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_album_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val album = arguments?.getParcelable<Album>("Album")
        view.findViewById<TextView>(R.id.tv_fragment_desc).text = album?.tracks?.toString()


        lifecycleScope.launch(Dispatchers.IO) {
            repository.searchArtist("Travis").collect {
                val travisArtist = it[0]
                repository.getArtistTopAlbums(travisArtist.name).collect {
                    val travisAlbum = it[0]
                    val travisAlbumDetail = repository.getArtistAlbum(travisAlbum.artist.name, travisAlbum.name)
                    repository.saveAlbum(travisAlbumDetail)
                }
            }
        }
    }
}