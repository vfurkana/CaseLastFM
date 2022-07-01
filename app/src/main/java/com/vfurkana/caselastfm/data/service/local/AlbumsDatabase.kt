package com.vfurkana.caselastfm.data.service.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vfurkana.caselastfm.data.service.local.dao.AlbumsDao
import com.vfurkana.caselastfm.data.service.local.model.AlbumEntity

@Database(entities = [AlbumEntity::class], version = 1, exportSchema = true)
abstract class AlbumsDatabase : RoomDatabase() {
    abstract fun albumsDao(): AlbumsDao
}