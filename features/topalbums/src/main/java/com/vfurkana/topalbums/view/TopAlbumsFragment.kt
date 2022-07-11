package com.vfurkana.topalbums.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vfurkana.caselastfm.common.domain.model.Artist
import com.vfurkana.caselastfm.common.domain.model.TopAlbum
import com.vfurkana.caselastfm.common.view.BaseFragment
import com.vfurkana.caselastfm.common.view.PagedRecyclerViewLoadStateAdapter
import com.vfurkana.caselastfm.common.view.ViewState
import com.vfurkana.topalbums.R
import com.vfurkana.topalbums.databinding.FragmentTopAlbumsBinding
import com.vfurkana.topalbums.navigation.TopAlbumsNavigations
import com.vfurkana.topalbums.viewmodel.ArtistTopAlbumsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TopAlbumsFragment : BaseFragment<FragmentTopAlbumsBinding>() {

    val viewModel: ArtistTopAlbumsViewModel by viewModels()
    private val recyclerViewAdapter = TopAlbumsRecyclerViewAdapter(::onItemClick, ::onSaveClick)

    @Inject lateinit var navigator: TopAlbumsNavigations

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean): FragmentTopAlbumsBinding {
        return FragmentTopAlbumsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding?.recyclerViewTopAlbums?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding?.recyclerViewTopAlbums?.adapter =
            recyclerViewAdapter.withLoadStateFooter(PagedRecyclerViewLoadStateAdapter(this@TopAlbumsFragment::retry))
        viewBinding?.recyclerViewTopAlbums?.addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))

        arguments?.getParcelable<Artist>(KEY_EXTRAS_ARTIST)?.let {
            initializeForArtist(it.name)
        }
    }

    private var currentJob: Job? = null
    private fun initializeForArtist(artistName: String) {
        currentJob?.cancel()
        currentJob = null
        currentJob = lifecycleScope.launch {
            launch {
                recyclerViewAdapter.loadStateFlow.collectLatest { loadState ->
                    if (loadState.refresh is LoadState.Loading) {
                        viewBinding?.stateView?.updateWithViewState(ViewState.Progress)
                    } else if (loadState.refresh is LoadState.NotLoading) {
                        viewBinding?.stateView?.showContent()
                    } else if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && recyclerViewAdapter.itemCount < 1) {
                        viewBinding?.stateView?.updateWithViewState(ViewState.Empty())
                    }
                    (loadState.refresh as? LoadState.Error)?.let { viewBinding?.stateView?.updateWithViewState(ViewState.Error(it.error)) }
                }
            }
            launch {
                viewModel.getTopAlbumsForArtist((artistName))
            }
            launch {
                viewModel.topAlbumsFlow.collectLatest {
                    viewBinding?.stateView?.updateWithViewState(it)
                    if (it is ViewState.Success) {
                        recyclerViewAdapter.submitData(it.data)
                    }
                }
            }
            launch {
                viewModel.albumSaveStatus.collect {
                    recyclerViewAdapter.notifyItemChanged(it.first)
                }
            }

        }
    }

    private fun onItemClick(album: TopAlbum) {
        navigator.navigateToDetail(album)
    }

    private fun onSaveClick(position: Int, album: TopAlbum, isSave: Boolean) {
        lifecycleScope.launch {
            viewModel.saveAlbum(album, position, isSave)
        }
    }

    private fun retry() {
        arguments?.getParcelable<Artist>(KEY_EXTRAS_ARTIST)?.let {
            initializeForArtist(it.name)
        }
    }

    companion object {
        val KEY_EXTRAS_ARTIST = "EXTRAS_ARTIST"
    }
}