package com.vfurkana.caselastfm.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val size: ImageSize?,
    val url: String
) : Parcelable {
    enum class ImageSize {
        ExtraLarge,
        Large,
        Medium,
        Mega,
        Small;
    }
}
