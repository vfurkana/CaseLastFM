package com.vfurkana.caselastfm.common.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopAlbum(
    val name: String,
    val url: String,
    val artistName: String,
    val image: List<Image>,
    var isSaved : Boolean? = null
) : Parcelable