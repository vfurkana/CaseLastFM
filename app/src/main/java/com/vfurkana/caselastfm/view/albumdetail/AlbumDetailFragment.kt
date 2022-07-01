package com.vfurkana.caselastfm.view.albumdetail

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
import com.vfurkana.caselastfm.databinding.FragmentAlbumDetailBinding
import com.vfurkana.caselastfm.domain.model.Album
import com.vfurkana.caselastfm.domain.model.Artist
import com.vfurkana.caselastfm.domain.model.Image
import com.vfurkana.caselastfm.domain.model.Track
import com.vfurkana.caselastfm.view.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlbumDetailFragment : BaseFragment<FragmentAlbumDetailBinding>() {
    @Inject lateinit var repository: LastFMRepository

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean): FragmentAlbumDetailBinding {
        return FragmentAlbumDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val album = arguments?.getParcelable<Album>("Album")
        view.findViewById<TextView>(R.id.tv_fragment_desc).text = album?.tracks?.toString()


    }
}