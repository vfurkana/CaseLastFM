package com.vfurkana.caselastfm.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopAlbum(
    val name: String,
    val url: String,
    val artistName: String,
    val image: List<Image>
) : Parcelable