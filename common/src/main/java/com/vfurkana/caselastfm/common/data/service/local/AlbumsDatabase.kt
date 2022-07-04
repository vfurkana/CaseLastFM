package com.vfurkana.caselastfm.common.data.service.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vfurkana.caselastfm.common.data.service.local.dao.AlbumsDao
import com.vfurkana.caselastfm.common.data.service.local.model.AlbumEntity

@Database(entities = [com.vfurkana.caselastfm.common.data.service.local.model.AlbumEntity::class], version = 1, exportSchema = true)
abstract class AlbumsDatabase : RoomDatabase() {
    abstract fun albumsDao(): com.vfurkana.caselastfm.common.data.service.local.dao.AlbumsDao
}