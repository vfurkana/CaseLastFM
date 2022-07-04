package com.vfurkana.caselastfm.albumdetail.view

import androidx.recyclerview.widget.DiffUtil
import com.vfurkana.caselastfm.common.domain.model.Track

object TrackDiffCallback : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.name == newItem.name
    }
    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }
}