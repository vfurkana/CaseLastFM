package com.vfurkana.caselastfm.view.albumdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.vfurkana.caselastfm.databinding.FragmentAlbumDetailBinding
import com.vfurkana.caselastfm.domain.model.Album
import com.vfurkana.caselastfm.domain.model.Image
import com.vfurkana.caselastfm.domain.model.TopAlbum
import com.vfurkana.caselastfm.view.common.BaseFragment
import com.vfurkana.caselastfm.view.common.ViewState
import com.vfurkana.caselastfm.viewmodel.albumdetail.AlbumDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
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


        arguments?.let { bundle -> initializeWithArguments(bundle) }
    }


    private var currentJob: Job? = null
    private fun initializeWithArguments(bundle: Bundle) {
        currentJob?.cancel()
        currentJob = null
        bundle.getParcelable<Album>(KEY_EXTRAS_ALBUM)?.also {
            viewModel.useAlbumDetail(it)
        } ?: bundle.getParcelable<TopAlbum>(KEY_EXTRAS_TOP_ALBUM)?.also {
            viewModel.getAlbumDetail(it)
        }
        currentJob = lifecycleScope.launch {
            viewModel.albumDetails.collectLatest { albumDetail ->
                viewBinding?.stateView?.updateWithViewState(albumDetail)
                if (albumDetail is ViewState.Success) {
                    viewBinding?.imageViewAlbumImage?.let {
                        Glide.with(it)
                            .load(albumDetail.data.image.maxWith(Image.ImageSize.ImageBySizeComparator).url)
                            .into(it)
                    }
                    viewBinding?.textViewAlbumName?.text = albumDetail.data.name
                    viewBinding?.textViewArtistName?.text = albumDetail.data.artistName
                    albumDetail.data.tracks?.let { tracksAdapter.submitList(it) }

                    launch {
                        viewBinding?.toggleSave?.isEnabled = true
                        viewBinding?.toggleSave?.setOnTouchListener { view, motionEvent ->
                            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                                if (viewBinding?.toggleSave?.isChecked == true) { viewModel.removeAlbum(albumDetail.data)
                                } else { viewModel.saveAlbum(albumDetail.data) };true
                            } else { false } }
                        viewModel.saveStatus.collectLatest {
                            viewBinding?.toggleSave?.isChecked = it
                        }
                    }
                }
            }
        }
    }


    companion object {
        val KEY_EXTRAS_ALBUM = "EXTRAS_ALBUM"
        val KEY_EXTRAS_TOP_ALBUM = "EXTRAS_TOP_ALBUM"
    }
}