package com.vfurkana.caselastfm.viewmodel.artistsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vfurkana.caselastfm.domain.usecase.ArtistSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistSearchViewModel @Inject constructor(val useCase: ArtistSearchUseCase) : ViewModel() {

    private val searchFlow = MutableSharedFlow<String?>()
    val searchResults = searchFlow.debounce(500).flatMapLatest {
        useCase.searchArtistsPaged(it, viewModelScope)
    }

    fun onSearchInput(input: String?) {
        viewModelScope.launch {
            searchFlow.emit(input)
        }
    }
}