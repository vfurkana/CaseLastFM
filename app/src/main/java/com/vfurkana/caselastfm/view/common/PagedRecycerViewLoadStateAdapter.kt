package com.vfurkana.caselastfm.view.common

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vfurkana.caselastfm.databinding.RowLoadStateBinding

class PagedRecyclerViewLoadStateAdapter(val retryListener: () -> Unit) : LoadStateAdapter<PagedRecyclerViewLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(
        private val binding: RowLoadStateBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        private val progressBar: ProgressBar = binding.progressBar
        private val errorMsg: TextView = binding.textViewErrorMsg
        private val retry: Button = binding.buttonRetry
            .also {
                it.setOnClickListener { retryListener() }
            }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                errorMsg.text = loadState.error.localizedMessage
            }

            progressBar.isVisible = loadState is LoadState.Loading
            retry.isVisible = loadState is LoadState.Error
            errorMsg.isVisible = loadState is LoadState.Error
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(RowLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}