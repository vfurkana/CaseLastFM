package com.vfurkana.caselastfm.data.service.remote.model

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("#text") val url: String,
    val size: Size?
)

enum class Size {
    @SerializedName("extralarge") ExtraLarge,
    @SerializedName("large") Large,
    @SerializedName("medium") Medium,
    @SerializedName("mega") Mega,
    @SerializedName("small") Small;
}