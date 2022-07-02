package com.vfurkana.caselastfm.viewmodel.savedalbums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vfurkana.caselastfm.domain.model.Album
import com.vfurkana.caselastfm.domain.usecase.SavedAlbumsUseCase
import com.vfurkana.caselastfm.view.common.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SavedAlbumsViewModel @Inject constructor(private val savedAlbumsUseCase: SavedAlbumsUseCase) : ViewModel() {

    private val savedAlbumsFlowInner: MutableStateFlow<ViewState<PagingData<Album>>> = MutableStateFlow(ViewState.Initial())
    val savedAlbumsFlow: Flow<ViewState<PagingData<Album>>> = savedAlbumsFlowInner.onSubscription {
        savedAlbumsUseCase
            .getSavedAlbums()
            .cachedIn(viewModelScope)
            .onStart { emit(ViewState.Progress) }
            .onEmpty { emit(ViewState.Empty()) }
            .map { ViewState.Success(it) }
            .catch { emit(ViewState.Error(it)) }
            .collect(this)
    }
}