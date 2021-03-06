package com.vfurkana.topalbums.view

import androidx.recyclerview.widget.DiffUtil
import com.vfurkana.caselastfm.common.domain.model.TopAlbum

object TopAlbumDiffCallback : DiffUtil.ItemCallback<TopAlbum>() {
    override fun areItemsTheSame(oldItem: TopAlbum, newItem: TopAlbum): Boolean {
        return oldItem.name == newItem.name

    }

    override fun areContentsTheSame(oldItem: TopAlbum, newItem: TopAlbum): Boolean {
        return oldItem == newItem
    }
}