package com.vfurkana.caselastfm.view.artisttopalbums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vfurkana.caselastfm.databinding.RowTopAlbumBinding
import com.vfurkana.caselastfm.domain.model.Image
import com.vfurkana.caselastfm.domain.model.TopAlbum

class TopAlbumsRecyclerViewAdapter(val itemSelectListener: (TopAlbum) -> Unit) :
    PagingDataAdapter<TopAlbum, TopAlbumsRecyclerViewAdapter.TopAlbumViewHolder>(
        TopAlbumDiffCallback
    ) {

    inner class TopAlbumViewHolder(private val binding: RowTopAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: TopAlbum?) {
            album?.let {
                binding.root.setOnClickListener { itemSelectListener(album) }
                binding.textViewAlbumName.text = album.name
                binding.textViewUrl.text = album.url
                binding.textViewArtistName.text = album.artistName
                val largestImage = it.image.maxWith(Image.ImageSize.ImageBySizeComparator)
                Glide.with(itemView).load(largestImage.url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(binding.imageViewAlbumImage)
            }
        }
    }

    override fun onBindViewHolder(holder: TopAlbumViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopAlbumViewHolder {
        return TopAlbumViewHolder(
            RowTopAlbumBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}