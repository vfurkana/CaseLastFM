package com.vfurkana.caselastfm.view.albumdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.vfurkana.caselastfm.databinding.FragmentAlbumDetailBinding
import com.vfurkana.caselastfm.domain.model.*
import com.vfurkana.caselastfm.view.common.BaseFragment
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

        lifecycleScope.launch {
            viewModel.albumDetails.collectLatest { albumDetail ->
                viewBinding?.imageViewAlbumImage?.let { Glide.with(it).load(albumDetail.image.maxWith(ImageBySizeComparator).url).into(it) }
                viewBinding?.textViewAlbumName?.text = albumDetail.name
                viewBinding?.textViewArtistName?.text = albumDetail.artistName
                albumDetail.tracks?.let { tracksAdapter.submitList(it) }
//                viewBinding?.floatingActionButtonSave?.setOnClickListener {
//                    viewModel.saveAlbum(albumDetail)
//                }
            }
        }

        arguments?.let { bundle ->
            bundle.getParcelable<Album>(KEY_EXTRAS_ALBUM)?.also {
                viewModel.useAlbumDetail(it) } ?:
            bundle.getParcelable<TopAlbum>(KEY_EXTRAS_TOP_ALBUM)?.also {
                viewModel.getAlbumDetail(it) }
        }
    }


    companion object {
        val KEY_EXTRAS_ALBUM = "EXTRAS_ALBUM"
        val KEY_EXTRAS_TOP_ALBUM = "EXTRAS_TOP_ALBUM"
    }
}