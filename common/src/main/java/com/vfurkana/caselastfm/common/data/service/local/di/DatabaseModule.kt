package com.vfurkana.caselastfm.common.data.service.local.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.vfurkana.caselastfm.common.data.service.local.AlbumsDatabase
import com.vfurkana.caselastfm.common.data.service.local.model.LastFMTypeConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DB_NAME = "albums.db"

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideTypeConverters(gson: Gson) = LastFMTypeConverters(gson)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context, typeConverters: LastFMTypeConverters): AlbumsDatabase {
        return Room
            .databaseBuilder(context, AlbumsDatabase::class.java, DB_NAME)
            .addTypeConverter(typeConverters)
            .build()
    }


    @Provides
    fun provideAlbumsDao(
        albumsDatabase: AlbumsDatabase
    ): com.vfurkana.caselastfm.common.data.service.local.dao.AlbumsDao {
        return albumsDatabase.albumsDao()
    }

}