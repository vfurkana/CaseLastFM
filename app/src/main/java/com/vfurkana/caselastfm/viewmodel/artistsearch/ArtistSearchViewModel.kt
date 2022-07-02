package com.vfurkana.caselastfm.viewmodel.artistsearch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vfurkana.caselastfm.domain.model.Artist
import com.vfurkana.caselastfm.domain.usecase.ArtistSearchUseCase
import com.vfurkana.caselastfm.view.common.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistSearchViewModel @Inject constructor(val useCase: ArtistSearchUseCase) : ViewModel() {

    private val queryFlow = MutableSharedFlow<String?>()
    val searchResults: Flow<ViewState<PagingData<Artist>>> =
        queryFlow
            .debounce(500)
            .filterNot { it.isNullOrEmpty() }
            .flatMapLatest {
                useCase.searchArtistsPaged(it)
                    .cachedIn(viewModelScope)
                    .map<PagingData<Artist>, ViewState<PagingData<Artist>>> { ViewState.Success(it) }
                    .onStart { emit(ViewState.Progress) }
                    .onEmpty { emit(ViewState.Empty()) }
                    .catch { emit(ViewState.Error(it)) }
            }
            .onStart { emit(ViewState.Initial()) }

    fun onSearchInput(input: String?) {
        viewModelScope.launch { queryFlow.emit(input) }
    }
}