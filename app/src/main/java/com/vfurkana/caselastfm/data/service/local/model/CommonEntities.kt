package com.vfurkana.caselastfm.data.service.local.model


data class ImageEntity(
    val url: String,
    val size: SizeEntity?
)

enum class SizeEntity {
    ExtraLarge,
    Large,
    Medium,
    Mega,
    Small;
}