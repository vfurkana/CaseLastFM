package com.vfurkana.caselastfm.viewmodel.albumdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vfurkana.caselastfm.domain.model.Album
import com.vfurkana.caselastfm.domain.model.TopAlbum
import com.vfurkana.caselastfm.domain.usecase.AlbumDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(val albumDetailsUseCase: AlbumDetailsUseCase) : ViewModel() {

    private val albumDetailsInner: MutableSharedFlow<Album> = MutableSharedFlow()
    val albumDetails: Flow<Album> = albumDetailsInner

    fun getAlbumDetail(album: TopAlbum) {
        viewModelScope.launch {
            albumDetailsInner.emit(albumDetailsUseCase.getAlbumDetails(album.name, album.artistName))
        }
    }

    fun useAlbumDetail(album: Album) {
        viewModelScope.launch {
            albumDetailsInner.emit(album)
        }
    }
}