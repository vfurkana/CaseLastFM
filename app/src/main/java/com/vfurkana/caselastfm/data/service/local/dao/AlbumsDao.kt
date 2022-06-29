package com.vfurkana.caselastfm.data.service.local.dao

import androidx.room.*
import com.vfurkana.caselastfm.data.service.local.model.AlbumEntity
import com.vfurkana.caselastfm.data.service.local.model.ArtistEntity

@Dao
abstract class AlbumsDao {
    @Query("SELECT * FROM albums")
    abstract suspend fun getAlbums(): List<AlbumEntity>

    @Query("SELECT * FROM albums JOIN artists ON albums.artist = artists.artistName")
    abstract fun getAlbumsAndArtist(): Map<AlbumEntity, List<ArtistEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAlbum(albumEntity: AlbumEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertArtist(artistEntity: ArtistEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAllAlbums(albumEntities: List<AlbumEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAllBaseAlbums(albumEntities: List<AlbumEntity>)

    @Query("DELETE FROM albums")
    abstract suspend fun clearAll()

}