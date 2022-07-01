package com.vfurkana.caselastfm.view.artisttopalbums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vfurkana.caselastfm.R
import com.vfurkana.caselastfm.databinding.FragmentTopAlbumsBinding
import com.vfurkana.caselastfm.domain.model.Artist
import com.vfurkana.caselastfm.domain.model.TopAlbum
import com.vfurkana.caselastfm.view.albumdetail.AlbumDetailFragment
import com.vfurkana.caselastfm.view.common.BaseFragment
import com.vfurkana.caselastfm.view.common.PagedRecyclerViewLoadStateAdapter
import com.vfurkana.caselastfm.viewmodel.artisttopalbums.ArtistTopAlbumsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopAlbumsFragment : BaseFragment<FragmentTopAlbumsBinding>() {

    val viewModel: ArtistTopAlbumsViewModel by viewModels()
    private val recyclerViewAdapter = TopAlbumsRecyclerViewAdapter(::onItemClick)

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean): FragmentTopAlbumsBinding {
        return FragmentTopAlbumsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding?.recyclerViewTopAlbums?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding?.recyclerViewTopAlbums?.adapter =
            recyclerViewAdapter.withLoadStateFooter(PagedRecyclerViewLoadStateAdapter(this@TopAlbumsFragment::retry))

        lifecycleScope.launch {
            viewModel.topAlbumsFlow.collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }

        retry()
    }

    private fun retry() {
        arguments?.getParcelable<Artist>(KEY_EXTRAS_ARTIST)?.let {
            viewModel.getTopAlbumsForArtist((it.name))
        }
    }

    private fun onItemClick(album: TopAlbum) {
        findNavController().navigate(R.id.action_top_albums_to_detail, bundleOf(AlbumDetailFragment.KEY_EXTRAS_TOP_ALBUM to album))
    }

    companion object {
        val KEY_EXTRAS_ARTIST = "EXTRAS_ARTIST"
    }
}