package com.vfurkana.savedalbums.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vfurkana.caselastfm.common.domain.model.Album
import com.vfurkana.caselastfm.common.domain.model.Image
import com.vfurkana.savedalbums.R
import com.vfurkana.savedalbums.databinding.RowAlbumBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class SavedAlbumsRecyclerViewAdapter(
    val itemSelectListener: (Album) -> Unit,
    val unSaveClickListener: (album: Album) -> Unit
) :
    PagingDataAdapter<Album, SavedAlbumsRecyclerViewAdapter.AlbumViewHolder>(
        AlbumDiffCallback
    ) {

    inner class AlbumViewHolder(private val binding: RowAlbumBinding) : RecyclerView.ViewHolder(binding.root) {

        private var lastCountDownJob: Job? = null
        private fun countDownToRemove(album: Album) {
            if (lastCountDownJob == null || lastCountDownJob?.isCancelled == true || lastCountDownJob?.isActive != true) {
                lastCountDownJob = itemView.findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
                    flow {
                        for (i in 5 downTo 0) {
                            emit(i); delay(1000)
                        }
                    }.flowOn(Dispatchers.IO)
                        .collect {
                            binding.buttonRemove.text = it.toString()
                        }
                    binding.buttonRemove.isEnabled = false;
                    unSaveClickListener(album)
                }
            } else {
                lastCountDownJob?.cancel()
                binding.buttonRemove.text = itemView.context.getString(R.string.Remove)
            }
        }

        fun bind(album: Album?) {
            album?.let {
                binding.root.setOnClickListener { itemSelectListener(album) }
                binding.textViewAlbumName.text = album.name
                binding.textViewArtistName.text = album.artistName

                binding.buttonRemove.setOnClickListener {
                    countDownToRemove(album)
                }

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