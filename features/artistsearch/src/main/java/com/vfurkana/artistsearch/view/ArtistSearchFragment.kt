package com.vfurkana.artistsearch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vfurkana.albumsearch.databinding.FragmentSearchArtistBinding
import com.vfurkana.artistsearch.navigation.ArtistSearchNavigations
import com.vfurkana.artistsearch.viewmodel.ArtistSearchViewModel
import com.vfurkana.caselastfm.common.domain.model.Artist
import com.vfurkana.caselastfm.common.view.BaseFragment
import com.vfurkana.caselastfm.common.view.PagedRecyclerViewLoadStateAdapter
import com.vfurkana.caselastfm.common.view.ViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ArtistSearchFragment : BaseFragment<FragmentSearchArtistBinding>() {

    private val viewModel: ArtistSearchViewModel by viewModels()

    private val recyclerViewAdapter = ArtistSearchRecyclerViewAdapter(::navigateToTopAlbums)

    @Inject lateinit var navigator: ArtistSearchNavigations

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean): FragmentSearchArtistBinding {
        return FragmentSearchArtistBinding.inflate(inflater, container, attachToRoot)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding?.editTextSearchInput?.doAfterTextChanged {
            viewModel.onSearchInput(it?.toString())
        }
        viewBinding?.recyclerViewArtists?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding?.recyclerViewArtists?.adapter =
            recyclerViewAdapter.withLoadStateFooter(PagedRecyclerViewLoadStateAdapter(this@ArtistSearchFragment::retry))
        viewBinding?.recyclerViewArtists?.addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
        lifecycleScope.launch {
            recyclerViewAdapter.loadStateFlow.collectLatest { loadState ->
                if (loadState.refresh is LoadState.Loading) {
                    viewBinding?.stateView?.updateWithViewState(ViewState.Progress)
                } else if (loadState.refresh is LoadState.NotLoading) {
                    viewBinding?.stateView?.showContent()
                } else if (loadState.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached && recyclerViewAdapter.itemCount < 1
                ) {
                    viewBinding?.stateView?.updateWithViewState(ViewState.Empty())
                }
                (loadState.refresh as? LoadState.Error)?.let {
                    if (recyclerViewAdapter.itemCount <= 1) {
                        viewBinding?.stateView?.updateWithViewState(
                            ViewState.Error(it.error)
                        )
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.searchResults.collectLatest {
                viewBinding?.stateView?.updateWithViewState(it)
                if (it is ViewState.Success) {
                    recyclerViewAdapter.submitData(it.data)
                }
            }
        }
    }

    var currentJob: Job? = null
    private fun retry() {
        currentJob?.cancel()
        currentJob = null
        currentJob = lifecycleScope.launch {
            viewBinding?.editTextSearchInput?.text?.let {
                viewModel.onSearchInput(it.toString())
            }

        }
    }

    private fun navigateToTopAlbums(artist: Artist) {
        navigator.navigateToTopAlbums(artist)
    }
}