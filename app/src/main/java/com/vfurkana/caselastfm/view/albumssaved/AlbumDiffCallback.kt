package com.vfurkana.caselastfm.view.albumssaved

import androidx.recyclerview.widget.DiffUtil
import com.vfurkana.caselastfm.common.domain.model.Album

object AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.name == newItem.name

    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem == newItem
    }
}