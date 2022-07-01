package com.vfurkana.caselastfm.data.service.local.dao

import androidx.room.*
import com.vfurkana.caselastfm.data.service.local.model.AlbumEntity
import com.vfurkana.caselastfm.data.service.local.model.ArtistEntity
import com.vfurkana.caselastfm.data.service.local.model.SavedAlbumEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AlbumsAndArtistsDao {
    @Query("SELECT * FROM savedAlbums")
    abstract fun getSavedAlbums(): Flow<List<SavedAlbumEntity>>

    @Query("SELECT * FROM savedAlbums JOIN artists ON savedAlbums.artist = artists.artistName")
    abstract suspend fun getSavedAlbumsAndArtist(): Map<AlbumEntity, List<ArtistEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertSavedAlbum(albumEntity: SavedAlbumEntity)

    @Query("SELECT * FROM albums WHERE albumName LIKE :albumName LIMIT 1")
    abstract suspend fun getAlbum(albumName: String): AlbumEntity?

    @Query("SELECT * FROM albums")
    abstract suspend fun getAllAlbums(): List<AlbumEntity>

    @Query("SELECT * FROM albums JOIN artists ON albums.artist = artists.artistName")
    abstract suspend fun getAlbumsAndArtist(): Map<AlbumEntity, List<ArtistEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAlbum(albumEntity: AlbumEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertArtist(artistEntity: ArtistEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAllAlbums(albumEntities: List<AlbumEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAllArtists(artistEntities: List<ArtistEntity>)

    @Transaction
    open suspend fun clearAll() {
        clearAlbums()
        clearArtists()
    }

    @Query("DELETE FROM albums")
    abstract suspend fun clearAlbums()

    @Query("DELETE FROM artists")
    abstract suspend fun clearArtists()
}