package com.vfurkana.caselastfm.view.albumssaved

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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SavedAlbumsFragment : Fragment() {
    @Inject lateinit var repository: LastFMRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_saved_albums, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.tv_fragment_desc).setOnClickListener {
            findNavController().navigate(R.id.action_saved_to_detail)
        }
        refreshSavedAlbums()

        lifecycleScope.launch(Dispatchers.IO) {
            repository.searchArtist("Pink Floyd").collect {
                val firstArtist = it[0]
                repository.getArtistTopAlbums(firstArtist.name).collect {
                    val firstAlbum = it[0]
                    val firstAlbumDetail = repository.getArtistAlbum(firstArtist.name, firstAlbum.name)
                    view.findViewById<TextView>(R.id.tv_fragment_desc).setOnClickListener {
                        findNavController().navigate(
                            R.id.action_saved_to_detail,
                            bundleOf(
                                "Album" to Album(
                                    firstAlbumDetail.name,
                                    Artist(firstAlbumDetail.artist),
                                    firstAlbumDetail.image.map { Image(it.size?.name?.let { Image.ImageSize.valueOf(it) }, it.url) },
                                    firstAlbumDetail.tracks?.track?.map { Track(it.duration, it.url, it.name) }
                                )
                            )
                        )
                    }
                    repository.saveAlbum(firstAlbumDetail)
                    refreshSavedAlbums()
                }
            }
        }
    }

    private fun refreshSavedAlbums() {
        lifecycleScope.launch(Dispatchers.IO) {
            repository.getSavedAlbums().collectLatest {
                val string = it.map { "${it.tracks?.track?.map { it.name }} \n" }.toString()
                withContext(Dispatchers.Main) {
                    view?.findViewById<TextView>(R.id.tv_fragment_desc)?.text = string
                }
            }
        }
    }
}