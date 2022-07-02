package com.vfurkana.caselastfm.viewmodel.artisttopalbums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vfurkana.caselastfm.domain.model.Album
import com.vfurkana.caselastfm.domain.model.TopAlbum
import com.vfurkana.caselastfm.domain.usecase.AlbumDetailsUseCase
import com.vfurkana.caselastfm.domain.usecase.ArtistTopAlbumsUseCase
import com.vfurkana.caselastfm.view.common.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistTopAlbumsViewModel @Inject constructor(private val topAlbumsUseCase: ArtistTopAlbumsUseCase) : ViewModel() {

    private val topAlbumsFlowInner: MutableSharedFlow<ViewState<PagingData<TopAlbum>>> = MutableStateFlow(ViewState.Initial())
    val topAlbumsFlow: Flow<ViewState<PagingData<TopAlbum>>> = topAlbumsFlowInner

    fun getTopAlbumsForArtist(artist: String) {
        viewModelScope.launch {
            topAlbumsUseCase.getTopAlbumsPaged(artist)
                .cachedIn(viewModelScope)
                .onStart { ViewState.Progress }
                .onEmpty { ViewState.Empty() }
                .map { ViewState.Success(it) }
                .catch { ViewState.Error(it) }
                .collect(topAlbumsFlowInner)
        }
    }
}