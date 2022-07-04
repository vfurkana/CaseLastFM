package com.vfurkana.caselastfm.common.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Artist(
    val name: String,
    val image: List<Image>,
    val listeners: String,
    val url: String
) : Parcelable