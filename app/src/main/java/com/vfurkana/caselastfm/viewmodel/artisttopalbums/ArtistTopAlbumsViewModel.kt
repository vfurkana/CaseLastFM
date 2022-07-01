package com.vfurkana.caselastfm.viewmodel.artisttopalbums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.vfurkana.caselastfm.domain.model.TopAlbum
import com.vfurkana.caselastfm.domain.usecase.ArtistTopAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ArtistTopAlbumsViewModel @Inject constructor(private val topAlbumsUseCase: ArtistTopAlbumsUseCase) : ViewModel() {
    fun getTopAlbumsForArtist(artist: String): Flow<PagingData<TopAlbum>> {
        return topAlbumsUseCase.getTopAlbumsPaged(artist, viewModelScope)
    }
}