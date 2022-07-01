package com.vfurkana.caselastfm.data.repository.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vfurkana.caselastfm.data.service.remote.api.LastFMAPI
import com.vfurkana.caselastfm.data.service.remote.model.ArtistApiResponse
import javax.inject.Inject

class SearchArtistsPagingResource constructor(val query: String?, val lastFMAPI: LastFMAPI) : PagingSource<Int, ArtistApiResponse>() {

    private val initialPageIndex = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtistApiResponse> {
        val page = params.key ?: initialPageIndex
        try {
            if (query.isNullOrEmpty()) return LoadResult.Error(Exception("Invalid query"))
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

    override fun getRefreshKey(state: PagingState<Int, ArtistApiResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}