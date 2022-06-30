package com.vfurkana.caselastfm.data.service.remote.model

import com.google.gson.annotations.SerializedName

data class ImageAPIResponse(
    @SerializedName("#text") val url: String,
    val size: SizeAPIResponse?
)

enum class SizeAPIResponse {
    @SerializedName("extralarge") ExtraLarge,
    @SerializedName("large") Large,
    @SerializedName("medium") Medium,
    @SerializedName("mega") Mega,
    @SerializedName("small") Small;
}