package com.vfurkana.caselastfm.view.albumdetail

import androidx.recyclerview.widget.DiffUtil
import com.vfurkana.caselastfm.domain.model.Track

object TrackDiffCallback : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.name == newItem.name
    }
    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }
}