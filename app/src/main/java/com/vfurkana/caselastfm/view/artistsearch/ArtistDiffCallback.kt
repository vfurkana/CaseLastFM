package com.vfurkana.caselastfm.view.artistsearch

import androidx.recyclerview.widget.DiffUtil
import com.vfurkana.caselastfm.common.domain.model.Artist

object ArtistDiffCallback : DiffUtil.ItemCallback<Artist>() {
    override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem.name == newItem.name

    }

    override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem == newItem
    }
}