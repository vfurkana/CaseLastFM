package com.vfurkana.caselastfm.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Track(
    val duration: Long? = null,
    val url: String,
    val name: String,
    val artistName: String
) : Parcelable