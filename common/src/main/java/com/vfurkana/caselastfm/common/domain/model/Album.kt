package com.vfurkana.caselastfm.common.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    val name: String,
    val artistName: String,
    val image: List<Image>,
    val tracks: List<Track>?
) : Parcelable