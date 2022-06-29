package com.vfurkana.caselastfm.data.service.remote.model

import com.google.gson.annotations.SerializedName

data class SearchAPIResponseModel(
    val results: Results,
    val message: String,
    val error: Long
)

data class Results(
    @SerializedName("opensearch:Query") val opensearchQuery: OpensearchQuery,
    @SerializedName("opensearch:totalResults") val opensearchTotalResults: String,
    @SerializedName("opensearch:startIndex") val opensearchStartIndex: String,
    @SerializedName("opensearch:itemsPerPage") val opensearchItemsPerPage: String,
    @SerializedName("artistmatches") val artistMatches: ArtistMatches,
    @SerializedName("@attr") val callAttributes: SearchAPIAttr
)

data class ArtistMatches(
    @SerializedName("artist") val artists: List<Artist>
)

data class Artist(
    val name: String,
    val listeners: String,
    val mbid: String,
    val url: String,
    val streamable: String,
    val image: List<Image>
)

data class OpensearchQuery(
    @SerializedName("#text") val text: String,
    val role: String,
    val searchTerms: String,
    val startPage: String
)

data class SearchAPIAttr(
    @SerializedName("for") val searchedFor: String
)