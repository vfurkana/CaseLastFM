package com.vfurkana.caselastfm.view.albumdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.vfurkana.caselastfm.databinding.FragmentAlbumDetailBinding
import com.vfurkana.caselastfm.domain.model.*
import com.vfurkana.caselastfm.view.common.BaseFragment
import com.vfurkana.caselastfm.view.common.ViewState
import com.vfurkana.caselastfm.viewmodel.albumdetail.AlbumDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AlbumDetailFragment : BaseFragment<FragmentAlbumDetailBinding>() {

    val viewModel: AlbumDetailViewModel by viewModels()
    private val tracksAdapter = AlbumDetailTracksAdapter()

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean): FragmentAlbumDetailBinding {
        return FragmentAlbumDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding?.recyclerviewTracks?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding?.recyclerviewTracks?.adapter = tracksAdapter
        viewBinding?.recyclerviewTracks?.addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))

        lifecycleScope.launch {
            viewModel.albumDetails.collectLatest { albumDetail ->
                viewBinding?.stateView?.updateWithViewState(albumDetail)
                if (albumDetail is ViewState.Success) {
                    viewBinding?.imageViewAlbumImage?.let {
                        Glide.with(it).load(albumDetail.data.image.maxWith(Image.ImageSize.ImageBySizeComparator).url).into(it)
                    }
                    viewBinding?.textViewAlbumName?.text = albumDetail.data.name
                    viewBinding?.textViewArtistName?.text = albumDetail.data.artistName
                    albumDetail.data.tracks?.let { tracksAdapter.submitList(it) }
//                viewBinding?.floatingActionButtonSave?.setOnClickListener {
//                    viewModel.saveAlbum(albumDetail)
//                }
                }

            }
        }

        arguments?.let { bundle ->
            bundle.getParcelable<Album>(KEY_EXTRAS_ALBUM)?.also {
                viewModel.useAlbumDetail(it)
            } ?: bundle.getParcelable<TopAlbum>(KEY_EXTRAS_TOP_ALBUM)?.also {
                viewModel.getAlbumDetail(it)
            }
        }
    }


    companion object {
        val KEY_EXTRAS_ALBUM = "EXTRAS_ALBUM"
        val KEY_EXTRAS_TOP_ALBUM = "EXTRAS_TOP_ALBUM"
    }
}