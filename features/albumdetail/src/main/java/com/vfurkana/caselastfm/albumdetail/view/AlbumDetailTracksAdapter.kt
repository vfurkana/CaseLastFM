package com.vfurkana.caselastfm.albumdetail.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vfurkana.caselastfm.albumdetail.databinding.RowTrackBinding
import com.vfurkana.caselastfm.common.domain.model.Track
import java.util.concurrent.TimeUnit

class AlbumDetailTracksAdapter : ListAdapter<Track, AlbumDetailTracksAdapter.TrackViewHolder>(TrackDiffCallback) {
    inner class TrackViewHolder(private val binding: RowTrackBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track) {
            binding.textViewTrackName.text = track.name
            binding.textViewUrl.text = track.url
            binding.textViewLength.text = track.duration?.let {
                String.format(
                    "%d:%02d:%02d",
                    TimeUnit.SECONDS.toHours(it),
                    TimeUnit.SECONDS.toMinutes(it) % 60,
                    TimeUnit.SECONDS.toSeconds(it) % 60
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(RowTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}