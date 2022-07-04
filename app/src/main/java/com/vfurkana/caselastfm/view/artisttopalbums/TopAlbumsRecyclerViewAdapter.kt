package com.vfurkana.caselastfm.view.artisttopalbums

import android.view.LayoutInflater
import android.view.MotionEvent.ACTION_DOWN
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vfurkana.caselastfm.common.domain.model.Image
import com.vfurkana.caselastfm.databinding.RowTopAlbumBinding
import com.vfurkana.caselastfm.common.domain.model.TopAlbum

class TopAlbumsRecyclerViewAdapter(val itemSelectListener: (TopAlbum) -> Unit, val saveClickListener: (position: Int, album: TopAlbum, isSave: Boolean) -> Unit) :
    PagingDataAdapter<TopAlbum, TopAlbumsRecyclerViewAdapter.TopAlbumViewHolder>(
        TopAlbumDiffCallback
    ) {

    inner class TopAlbumViewHolder(private val binding: RowTopAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val album = getItem(position)
            album?.let {
                binding.textViewAlbumName.text = album.name
                binding.textViewUrl.text = album.url
                binding.textViewArtistName.text = album.artistName

                when (album.isSaved) {
                    null -> {
                        binding.toggleSave.isChecked = false; binding.toggleSave.isEnabled = false
                    }
                    true -> {
                        binding.toggleSave.isChecked = true; binding.toggleSave.isEnabled = true
                    }
                    false -> {
                        binding.toggleSave.isChecked = false; binding.toggleSave.isEnabled = true
                    }
                }

                binding.root.setOnClickListener { itemSelectListener(album) }
                binding.toggleSave.setOnTouchListener { view, motionEvent ->
                    if (motionEvent.action == ACTION_DOWN) {
                        saveClickListener(position, album, !binding.toggleSave.isChecked); true
                    } else { false } }

                Glide.with(itemView)
                    .load(it.image.maxWith(Image.ImageSize.ImageBySizeComparator).url)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(binding.imageViewAlbumImage)

            }
        }
    }

    override fun onBindViewHolder(holder: TopAlbumViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopAlbumViewHolder {
        return TopAlbumViewHolder(
            RowTopAlbumBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}