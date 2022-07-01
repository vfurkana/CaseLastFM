package com.vfurkana.caselastfm.view.artistsearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vfurkana.caselastfm.R
import com.vfurkana.caselastfm.databinding.RowArtistBinding
import com.vfurkana.caselastfm.domain.model.Artist
import com.vfurkana.caselastfm.domain.model.ImageBySizeComparator

class ArtistSearchRecyclerViewAdapter(val itemSelectListener: (Artist) -> Unit) :
    PagingDataAdapter<Artist, ArtistSearchRecyclerViewAdapter.ArtistViewHolder>(
        ArtistDiffCallback
    ) {

    inner class ArtistViewHolder(private val binding: RowArtistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Artist?) {
            artist?.let {
                binding.root.setOnClickListener { itemSelectListener(artist) }
                binding.textViewArtistName.text = artist.name
                binding.textViewUrl.text = artist.url
                binding.textViewListeners.text = artist.listeners
                val largestImage = it.image.maxWith(ImageBySizeComparator)
                Glide.with(itemView)
                    .load(largestImage.url)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .fallback(R.drawable.icon_image_placeholder)
                    .into(binding.imageViewArtistImage)
            }
        }
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        return ArtistViewHolder(
            RowArtistBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}