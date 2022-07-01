package com.vfurkana.caselastfm.data.service.local.dao

import androidx.room.*
import com.vfurkana.caselastfm.data.service.local.model.AlbumEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AlbumsDao {
    @Query("SELECT * FROM albums WHERE albumName LIKE :albumName LIMIT 1")
    abstract suspend fun getAlbum(albumName: String): AlbumEntity?

    @Query("SELECT * FROM albums")
    abstract fun getAllAlbums(): Flow<List<AlbumEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAlbum(albumEntity: AlbumEntity)

    @Query("DELETE FROM albums")
    abstract suspend fun clearAlbums()
}