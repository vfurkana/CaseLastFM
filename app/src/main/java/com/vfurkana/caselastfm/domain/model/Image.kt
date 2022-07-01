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
    val firstSize = first.size
    val secondSize = second.size
    if (firstSize == secondSize) 0
    else if (firstSize == null) -1
    else if (secondSize == null) 1
    else secondSize.compareTo(firstSize)
}