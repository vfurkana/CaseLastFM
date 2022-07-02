package com.vfurkana.caselastfm.view.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.vfurkana.caselastfm.R
import com.vfurkana.caselastfm.databinding.LayoutStateViewBinding

class StateView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    var emptyMessage: String?
    var errorMessage: String?
    var initialMessage: String?
    var progressMessage: String?
    var refreshMessage: String?
    var binding: LayoutStateViewBinding

    init {
        binding = LayoutStateViewBinding.inflate(LayoutInflater.from(context), this, true)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.StateView, 0, 0
        ).apply {
            try {
                emptyMessage = getString(R.styleable.StateView_emptyMessage)
                errorMessage = getString(R.styleable.StateView_errorMessage)
                initialMessage = getString(R.styleable.StateView_initialMessage)
                progressMessage = getString(R.styleable.StateView_progressMessage)
                refreshMessage = getString(R.styleable.StateView_refreshMessage)
            } finally {
                recycle()
            }
        }
        isFocusable = false
        isClickable = false
    }

    fun <T> updateWithViewState(viewState: ViewState<T>) {
        when (viewState) {
            is ViewState.Empty -> {
                binding.viewBlocking.isGone = true
                binding.progressBar.isGone = true
                binding.textViewMessage.isVisible = true
                binding.textViewMessage.text = emptyMessage
            }
            is ViewState.Error -> {
                binding.viewBlocking.isVisible = true
                binding.progressBar.isGone = true
                binding.textViewMessage.isVisible = true
                binding.textViewMessage.text = errorMessage
            }
            is ViewState.Initial -> {
                binding.viewBlocking.isGone = true
                binding.progressBar.isGone = true
                binding.textViewMessage.isVisible = true
                binding.textViewMessage.text = initialMessage
            }
            ViewState.Progress -> {
                binding.viewBlocking.isGone = true
                binding.progressBar.isVisible = true
                if (!progressMessage.isNullOrEmpty()) {
                    binding.textViewMessage.isVisible = true
                    binding.textViewMessage.text = progressMessage
                }
            }
            ViewState.Refresh -> {
                binding.viewBlocking.isVisible = true
                binding.progressBar.isVisible = true
                if (!progressMessage.isNullOrEmpty()) {
                    binding.textViewMessage.isVisible = true
                    binding.textViewMessage.text = refreshMessage
                }
            }
            is ViewState.Success -> {
                binding.viewBlocking.isGone = true
                binding.progressBar.isGone = true
                binding.textViewMessage.isGone = true
            }
        }
    }
}