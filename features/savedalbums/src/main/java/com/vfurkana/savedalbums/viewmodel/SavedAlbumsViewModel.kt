package com.vfurkana.savedalbums.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vfurkana.caselastfm.common.domain.model.Album
import com.vfurkana.caselastfm.common.view.ViewState
import com.vfurkana.savedalbums.domain.usecase.SavedAlbumsUseCase
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

    suspend fun unSaveAlbum(album: Album) {
        savedAlbumsUseCase.removeAlbum(album.name)
    }
}