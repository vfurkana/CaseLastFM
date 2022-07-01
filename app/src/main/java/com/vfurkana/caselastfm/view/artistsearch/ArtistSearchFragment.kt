package com.vfurkana.caselastfm.view.artistsearch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vfurkana.caselastfm.R
import com.vfurkana.caselastfm.data.service.remote.model.ArtistApiResponse
import com.vfurkana.caselastfm.databinding.FragmentSearchArtistBinding
import com.vfurkana.caselastfm.domain.model.Artist
import com.vfurkana.caselastfm.view.artisttopalbums.TopAlbumsFragment
import com.vfurkana.caselastfm.view.common.BaseFragment
import com.vfurkana.caselastfm.view.common.PagedRecyclerViewLoadStateAdapter
import com.vfurkana.caselastfm.viewmodel.artistsearch.ArtistSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArtistSearchFragment : BaseFragment<FragmentSearchArtistBinding>() {

    private val viewModel: ArtistSearchViewModel by viewModels()

    private val recyclerViewAdapter = ArtistSearchRecyclerViewAdapter(::onItemClick)

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean): FragmentSearchArtistBinding {
        return FragmentSearchArtistBinding.inflate(inflater, container, attachToRoot)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding?.editTextSearchInput?.doAfterTextChanged {
            viewModel.onSearchInput(it?.toString())
        }
        viewBinding?.recyclerViewArtists?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding?.recyclerViewArtists?.adapter = recyclerViewAdapter.withLoadStateFooter(PagedRecyclerViewLoadStateAdapter(this@ArtistSearchFragment::retry))
        lifecycleScope.launch {
            viewModel.searchResults.collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }


    private fun retry() {
        viewBinding?.editTextSearchInput?.text?.let {
            viewModel.onSearchInput(it.toString())
        }
    }

    private fun onItemClick(artist: Artist) {
        findNavController().navigate(R.id.action_search_to_top_albums, bundleOf(TopAlbumsFragment.KEY_EXTRAS_ARTIST to artist))
    }
}