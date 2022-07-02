package com.vfurkana.caselastfm.viewmodel.albumdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.vfurkana.caselastfm.domain.model.Album
import com.vfurkana.caselastfm.domain.model.TopAlbum
import com.vfurkana.caselastfm.domain.usecase.AlbumDetailsUseCase
import com.vfurkana.caselastfm.view.common.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(private val albumDetailsUseCase: AlbumDetailsUseCase) : ViewModel() {

    private val albumDetailsInner: MutableStateFlow<ViewState<Album>> = MutableStateFlow(ViewState.Initial())
    val albumDetails: Flow<ViewState<Album>> = albumDetailsInner

    fun getAlbumDetail(album: TopAlbum) {
        viewModelScope.launch {
            albumDetailsUseCase.getAlbumDetails(album.name, album.artistName)
                .map<Album, ViewState<Album>> { ViewState.Success(it) }
                .onStart { emit(ViewState.Progress) }
                .onEmpty { emit(ViewState.Empty()) }
                .catch { emit(ViewState.Error(it)) }
                .collect(albumDetailsInner)
        }
    }

    fun useAlbumDetail(album: Album) {
        viewModelScope.launch { albumDetailsInner.emit(ViewState.Success(album)) }
    }
}