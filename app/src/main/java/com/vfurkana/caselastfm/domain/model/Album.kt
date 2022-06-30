package com.vfurkana.caselastfm.domain.model

data class Album(
    val name: String,
    val artist: Artist,
    val image: Image,
    val tracks: Track
)