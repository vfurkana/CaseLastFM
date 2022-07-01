package com.vfurkana.caselastfm.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val size: ImageSize?,
    val url: String
) : Parcelable {
    enum class ImageSize {
        Mega,
        ExtraLarge,
        Large,
        Medium,
        Small;
    }
}


operator fun Image.ImageSize?.compareTo(second: Image.ImageSize): Int {
    return this?.compareTo(second) ?: -1
}