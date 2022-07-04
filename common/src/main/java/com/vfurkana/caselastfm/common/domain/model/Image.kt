package com.vfurkana.caselastfm.common.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val size: ImageSize?,
    val url: String
) : Parcelable {
    enum class ImageSize(private val sizeValue: Int) {
        Mega(5),
        ExtraLarge(4),
        Large(3),
        Medium(2),
        Small(1);

        companion object {
            val ImageBySizeComparator: Comparator<Image> = Comparator { first, second ->
                when {
                    first.size == second.size -> 0
                    second.size == null -> 1
                    first.size == null -> -1
                    else -> first.size.sizeValue.compareTo(second.size.sizeValue)
                }
            }
        }
    }
}