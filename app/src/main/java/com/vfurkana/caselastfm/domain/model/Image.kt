package com.vfurkana.caselastfm.domain.model

import retrofit2.http.Url

data class Image(
    val size: ImageSize,
    val url: Url
){
    enum class ImageSize {
        ExtraLarge,
        Large,
        Medium,
        Mega,
        Small;
    }
}
