package com.vfurkana.caselastfm.view.albumssaved

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
import com.vfurkana.caselastfm.databinding.FragmentSavedAlbumsBinding
import com.vfurkana.caselastfm.domain.model.Album
import com.vfurkana.caselastfm.view.albumdetail.AlbumDetailFragment
import com.vfurkana.caselastfm.view.common.BaseFragment
import com.vfurkana.caselastfm.viewmodel.savedalbums.SavedAlbumsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedAlbumsFragment : BaseFragment<FragmentSavedAlbumsBinding>() {

    val viewModel: SavedAlbumsViewModel by viewModels()
    private val recyclerViewAdapter = SavedAlbumsRecyclerViewAdapter(::onItemClick)

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean): FragmentSavedAlbumsBinding {
        return FragmentSavedAlbumsBinding.inflate(inflater, container, attachToRoot)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding?.recyclerViewSavedAlbums?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding?.recyclerViewSavedAlbums?.adapter = recyclerViewAdapter
        viewBinding?.floatingActionButtonSearch?.setOnClickListener {
            findNavController().navigate(R.id.action_saved_to_search)
        }

        lifecycleScope.launch {
            viewModel.savedAlbumsFlow.collectLatest {
                recyclerViewAdapter.submitList(it)
            }
        }
    }

    private fun onItemClick(album: Album) {
        findNavController().navigate(R.id.action_saved_to_detail, bundleOf(AlbumDetailFragment.KEY_EXTRAS_ALBUM to album))
    }
}