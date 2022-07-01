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

val ImageBySizeComparator: Comparator<Image> = Comparator { first, second ->
    when {
        first.size == second.size -> 0
        second.size == null -> 1
        first.size == null -> -1
        else -> first.size.compareTo(second.size)
    }
}