package com.vfurkana.artistsearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vfurkana.caselastfm.common.domain.model.Artist
import com.vfurkana.artistsearch.domain.usecase.ArtistSearchUseCase
import com.vfurkana.caselastfm.common.view.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistSearchViewModel @Inject constructor(val useCase: ArtistSearchUseCase) : ViewModel() {

    private val queryFlow = MutableSharedFlow<String?>()
    val searchResults: Flow<ViewState<PagingData<Artist>>> =
        queryFlow
            .distinctUntilChanged()
            .debounce(500)
            .filterNot { it.isNullOrEmpty() }
            .transform {
                useCase.searchArtistsPaged(it)
                    .cachedIn(viewModelScope)
                    .map<PagingData<Artist>, ViewState<PagingData<Artist>>> { ViewState.Success(it) }
                    .onStart { emit(ViewState.Progress) }
                    .onEmpty { emit(ViewState.Empty()) }
                    .catch { emit(ViewState.Error(it)) }
                    .collect(this)
            }
            .onStart { emit(ViewState.Initial()) }
            .shareIn(viewModelScope, SharingStarted.Eagerly)

    fun onSearchInput(input: String?) {
        viewModelScope.launch { queryFlow.emit(input) }
    }
}