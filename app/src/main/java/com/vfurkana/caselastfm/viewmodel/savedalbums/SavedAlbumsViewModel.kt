package com.vfurkana.caselastfm.viewmodel.savedalbums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.vfurkana.caselastfm.domain.model.Album
import com.vfurkana.caselastfm.domain.model.TopAlbum
import com.vfurkana.caselastfm.domain.usecase.AlbumDetailsUseCase
import com.vfurkana.caselastfm.domain.usecase.ArtistTopAlbumsUseCase
import com.vfurkana.caselastfm.domain.usecase.SavedAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedAlbumsViewModel @Inject constructor(private val savedAlbumsUseCase: SavedAlbumsUseCase) : ViewModel() {

    private val savedAlbumsFlowInner: MutableSharedFlow<List<Album>> = MutableSharedFlow()
    val savedAlbumsFlow: Flow<List<Album>> = savedAlbumsFlowInner.onSubscription {
        savedAlbumsUseCase.getSavedAlbums().collect(this)
    }

//    private fun getSavedAlbums() {
//        viewModelScope.launch {
//            savedAlbumsUseCase.getSavedAlbums().collect(savedAlbumsFlowInner)
//        }
//    }
}