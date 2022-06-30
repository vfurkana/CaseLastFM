package com.vfurkana.caselastfm.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    val name: String,
    val artist: Artist,
    val image: List<Image>,
    val tracks: List<Track>?
) : Parcelable