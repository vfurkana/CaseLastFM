package com.vfurkana.caselastfm.common.data.service.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.vfurkana.caselastfm.common.data.service.local.model.AlbumEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AlbumsDao {
    @Query("SELECT * FROM albums WHERE albumName LIKE :albumName LIMIT 1")
    abstract suspend fun getAlbum(albumName: String): AlbumEntity?

    @Query("SELECT * FROM albums WHERE albumName LIKE :albumName")
    abstract fun getAlbumFlow(albumName: String): Flow<List<AlbumEntity>>

    @Query("SELECT * FROM albums")
    abstract fun getAllAlbums(): PagingSource<Int, AlbumEntity>

    @Query("SELECT * FROM albums WHERE artist LIKE :artistName")
    abstract fun getAlbumsByArtist(artistName: String): List<AlbumEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAlbum(albumEntity: AlbumEntity)

    @Query("DELETE FROM albums WHERE albumName = :albumName")
    abstract suspend fun removeAlbum(albumName: String): Int

    @Query("DELETE FROM albums")
    abstract suspend fun clearAlbums()
}