package com.vfurkana.savedalbums.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vfurkana.caselastfm.common.domain.model.Album
import com.vfurkana.caselastfm.common.view.BaseFragment
import com.vfurkana.caselastfm.common.view.PagedRecyclerViewLoadStateAdapter
import com.vfurkana.caselastfm.common.view.ViewState
import com.vfurkana.savedalbums.databinding.FragmentSavedAlbumsBinding
import com.vfurkana.savedalbums.navigation.SavedAlbumsNavigations
import com.vfurkana.savedalbums.viewmodel.SavedAlbumsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SavedAlbumsFragment : BaseFragment<FragmentSavedAlbumsBinding>() {

    val viewModel: SavedAlbumsViewModel by viewModels()
    private val recyclerViewAdapter = SavedAlbumsRecyclerViewAdapter(::navigateToDetail)

    @Inject lateinit var navigator : SavedAlbumsNavigations

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean): FragmentSavedAlbumsBinding {
        return FragmentSavedAlbumsBinding.inflate(inflater, container, attachToRoot)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding?.recyclerViewSavedAlbums?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding?.recyclerViewSavedAlbums?.addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
        viewBinding?.recyclerViewSavedAlbums?.adapter = recyclerViewAdapter.withLoadStateFooter(PagedRecyclerViewLoadStateAdapter(::fetch))
        recyclerViewAdapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && recyclerViewAdapter.itemCount < 1) {
                viewBinding?.stateView?.updateWithViewState(ViewState.Empty())
            }
            (loadState.refresh as? LoadState.Error)?.let { viewBinding?.stateView?.updateWithViewState(ViewState.Error(it.error)) }
        }
        viewBinding?.floatingActionButtonSearch?.setOnClickListener {
            navigator.navigateToSearch()
        }
        fetch()
    }

    var currentJob: Job? = null
    private fun fetch() {
        currentJob?.cancel()
        currentJob = null
        currentJob = lifecycleScope.launch {
            viewModel.savedAlbumsFlow.collectLatest {
                viewBinding?.stateView?.updateWithViewState(it)
                if (it is ViewState.Success) {
                    recyclerViewAdapter.submitData(it.data)
                }
            }
        }
    }

    private fun navigateToDetail(album: Album) {
        navigator.navigateToDetail(album)
    }
}