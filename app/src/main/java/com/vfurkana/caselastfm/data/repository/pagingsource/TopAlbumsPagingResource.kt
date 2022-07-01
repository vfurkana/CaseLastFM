package com.vfurkana.caselastfm.data.repository.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vfurkana.caselastfm.data.service.remote.api.LastFMAPI
import com.vfurkana.caselastfm.data.service.remote.model.LastFMTopAlbumsAPIResponse

class TopAlbumsPagingResource constructor(private val query: String, private val lastFMAPI: LastFMAPI) : PagingSource<Int, LastFMTopAlbumsAPIResponse.TopAlbum>() {

    private val initialPageIndex = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LastFMTopAlbumsAPIResponse.TopAlbum> {
        val page = params.key ?: initialPageIndex
        try {
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

    override fun getRefreshKey(state: PagingState<Int, LastFMTopAlbumsAPIResponse.TopAlbum>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}