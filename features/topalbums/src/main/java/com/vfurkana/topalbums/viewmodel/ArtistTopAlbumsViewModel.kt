package com.vfurkana.topalbums.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vfurkana.caselastfm.common.domain.model.TopAlbum
import com.vfurkana.caselastfm.common.view.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistTopAlbumsViewModel @Inject constructor(private val topAlbumsUseCase: com.vfurkana.topalbums.domain.usecase.ArtistTopAlbumsUseCase) : ViewModel() {

    private val topAlbumsFlowInner: MutableSharedFlow<ViewState<PagingData<TopAlbum>>> = MutableStateFlow(ViewState.Initial())
    val topAlbumsFlow: Flow<ViewState<PagingData<TopAlbum>>> = topAlbumsFlowInner

    val albumSaveStatus = MutableSharedFlow<Pair<Int, ViewState<TopAlbum>>>()

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

    suspend fun saveAlbum(album: TopAlbum, position: Int, isSave: Boolean) {
        runCatching {
            album.isSaved = null
            albumSaveStatus.emit(position to ViewState.Progress)
            if (isSave) topAlbumsUseCase.saveAlbum(album.name, album.artistName)
            else topAlbumsUseCase.removeAlbum(album.name)
            isSave
        }.onSuccess {
            album.isSaved = it
            albumSaveStatus.emit(position to ViewState.Success(album))
        }.onFailure {
            album.isSaved = null
            albumSaveStatus.emit(position to ViewState.Error(it))
        }
    }
}