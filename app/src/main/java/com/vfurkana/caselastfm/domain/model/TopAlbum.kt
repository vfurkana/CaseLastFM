package com.vfurkana.caselastfm.domain.model

import android.os.Parcelable
import com.vfurkana.caselastfm.view.common.ViewState
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopAlbum(
    val name: String,
    val url: String,
    val artistName: String,
    val image: List<Image>,
    var isSaved : Boolean? = null
) : Parcelable