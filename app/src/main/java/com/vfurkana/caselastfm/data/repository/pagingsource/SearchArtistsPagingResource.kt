package com.vfurkana.caselastfm.data.repository.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vfurkana.caselastfm.data.service.remote.api.LastFMAPI
import com.vfurkana.caselastfm.data.service.remote.model.LastFMSearchArtistAPIResponseModel
import kotlinx.coroutines.supervisorScope

class SearchArtistsPagingResource constructor(private val query: String, private val lastFMAPI: LastFMAPI) :
    PagingSource<Int, LastFMSearchArtistAPIResponseModel.Artist>() {

    private val initialPageIndex = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LastFMSearchArtistAPIResponseModel.Artist> {
        val page = params.key ?: initialPageIndex
        return runCatching {
            val response = supervisorScope { lastFMAPI.searchArtist(query, page, params.loadSize) }
            response.results?.let {
                LoadResult.Page(
                    data = it.artistMatches.artists,
                    prevKey = (page - 1).let { if (it >= initialPageIndex) it else null },
                    nextKey = if ((it.opensearchStartIndex.toInt() + params.loadSize) < it.opensearchTotalResults.toInt()) page + 1 else null
                )
            } ?: LoadResult.Error(Exception(response.message))
        }.recoverCatching {
            LoadResult.Error(it)
        }.getOrThrow()
    }

    override fun getRefreshKey(state: PagingState<Int, LastFMSearchArtistAPIResponseModel.Artist>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}