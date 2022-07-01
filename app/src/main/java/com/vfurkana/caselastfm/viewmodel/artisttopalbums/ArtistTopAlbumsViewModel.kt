package com.vfurkana.caselastfm.viewmodel.artisttopalbums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.vfurkana.caselastfm.domain.model.Album
import com.vfurkana.caselastfm.domain.model.TopAlbum
import com.vfurkana.caselastfm.domain.usecase.AlbumDetailsUseCase
import com.vfurkana.caselastfm.domain.usecase.ArtistTopAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistTopAlbumsViewModel @Inject constructor(private val topAlbumsUseCase: ArtistTopAlbumsUseCase) : ViewModel() {

    private val topAlbumsFlowInner: MutableSharedFlow<PagingData<TopAlbum>> = MutableSharedFlow()
    val topAlbumsFlow: Flow<PagingData<TopAlbum>> = topAlbumsFlowInner

    fun getTopAlbumsForArtist(artist: String) {
        viewModelScope.launch {
            topAlbumsUseCase.getTopAlbumsPaged(artist, viewModelScope).collect(topAlbumsFlowInner)
        }
    }
}