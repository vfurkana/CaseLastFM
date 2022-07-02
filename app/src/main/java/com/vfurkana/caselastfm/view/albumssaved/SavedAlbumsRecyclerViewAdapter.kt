package com.vfurkana.caselastfm.view.albumssaved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vfurkana.caselastfm.databinding.RowAlbumBinding
import com.vfurkana.caselastfm.domain.model.Album
import com.vfurkana.caselastfm.domain.model.Image

class SavedAlbumsRecyclerViewAdapter(val itemSelectListener: (Album) -> Unit) :
    PagingDataAdapter<Album, SavedAlbumsRecyclerViewAdapter.AlbumViewHolder>(
        AlbumDiffCallback
    ) {

    inner class AlbumViewHolder(private val binding: RowAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album?) {
            album?.let {
                binding.root.setOnClickListener { itemSelectListener(album) }
                binding.textViewAlbumName.text = album.name
                binding.textViewArtistName.text = album.artistName
                val largestImage = it.image.maxWith(Image.ImageSize.ImageBySizeComparator)
                Glide.with(itemView).load(largestImage.url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(binding.imageViewAlbumImage)
            }
        }
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(
            RowAlbumBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}