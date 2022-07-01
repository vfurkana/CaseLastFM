package com.vfurkana.caselastfm.view.artistsearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vfurkana.caselastfm.databinding.RowArtistBinding
import com.vfurkana.caselastfm.domain.model.Artist

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
                val largestImage = it.image.maxWith { imageFirst, imageSecond ->
                    val sizeFirst = imageFirst.size
                    val sizeSecond = imageSecond.size
                    if (sizeFirst == sizeSecond) 0
                    else if (sizeFirst == null) -1
                    else if (sizeSecond == null) 1
                    else sizeFirst.compareTo(sizeSecond)
                }
                Glide.with(itemView).load(largestImage.url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(binding.imageViewArtistImage)
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