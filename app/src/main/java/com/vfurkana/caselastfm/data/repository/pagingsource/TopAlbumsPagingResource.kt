package com.vfurkana.caselastfm.data.repository.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vfurkana.caselastfm.data.service.remote.api.LastFMAPI
import com.vfurkana.caselastfm.data.service.remote.model.ArtistApiResponse
import com.vfurkana.caselastfm.data.service.remote.model.TopAlbumApiResponse
import javax.inject.Inject

class TopAlbumsPagingResource constructor(val query: String?, val lastFMAPI: LastFMAPI) : PagingSource<Int, TopAlbumApiResponse>() {

    private val initialPageIndex = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopAlbumApiResponse> {
        val page = params.key ?: initialPageIndex
        try {
            if (query.isNullOrEmpty()) return LoadResult.Error(Exception("Invalid query"))
            val response = lastFMAPI.getTopAlbumsByArtist(query, page, params.loadSize)
            return response.topAlbums.let {
                LoadResult.Page(
                    data = it.album,
                    prevKey = (it.attr.page.toInt() - 1).let { if (it >= initialPageIndex) it else null },
                    nextKey = if (it.attr.page.toInt() < it.attr.totalPages.toInt()) it.attr.page.toInt() + 1 else null,
                )
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TopAlbumApiResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}