package com.vfurkana.caselastfm.data.service.remote.model

import com.google.gson.annotations.SerializedName

data class SearchArtistAPIResponseModel(
    val results: ResultsApiResponse?,
    val message: String,
    val error: Long
)

data class ResultsApiResponse(
    @SerializedName("opensearch:Query") val opensearchQuery: OpensearchQueryApiResponse,
    @SerializedName("opensearch:totalResults") val opensearchTotalResults: String,
    @SerializedName("opensearch:startIndex") val opensearchStartIndex: String,
    @SerializedName("opensearch:itemsPerPage") val opensearchItemsPerPage: String,
    @SerializedName("artistmatches") val artistMatches: ArtistMatchesApiResponse,
    @SerializedName("@attr") val callAttributes: SearchAPIAttrApiResponse
)

data class ArtistMatchesApiResponse(
    @SerializedName("artist") val artists: List<ArtistApiResponse>
)

data class ArtistApiResponse(
    val name: String,
    val listeners: String,
    val mbid: String?,
    val url: String,
    val streamable: String?,
    val image: List<ImageAPIResponse>
)

data class OpensearchQueryApiResponse(
    @SerializedName("#text") val text: String,
    val role: String,
    val searchTerms: String,
    val startPage: String
)

data class SearchAPIAttrApiResponse(
    @SerializedName("for") val searchedFor: String
)