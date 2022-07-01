package com.vfurkana.caselastfm.data.repository.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vfurkana.caselastfm.data.service.remote.api.LastFMAPI
import com.vfurkana.caselastfm.data.service.remote.model.LastFMSearchArtistAPIResponseModel

class SearchArtistsPagingResource constructor(val query: String, val lastFMAPI: LastFMAPI) :
    PagingSource<Int, LastFMSearchArtistAPIResponseModel.Artist>() {

    private val initialPageIndex = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LastFMSearchArtistAPIResponseModel.Artist> {
        val page = params.key ?: initialPageIndex
        try {
            val response = lastFMAPI.searchArtist(query, page, params.loadSize)
            return response.results?.let {
                LoadResult.Page(
                    data = it.artistMatches.artists,
                    prevKey = (page - 1).let { if (it >= initialPageIndex) it else null },
                    nextKey = if ((it.opensearchStartIndex.toInt() + params.loadSize) < it.opensearchTotalResults.toInt()) page + 1 else null
                )
            } ?: LoadResult.Error(Exception(response.message))
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LastFMSearchArtistAPIResponseModel.Artist>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}