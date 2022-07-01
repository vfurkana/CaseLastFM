package com.vfurkana.caselastfm.data.service.local.model

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@ProvidedTypeConverter
class LastFMTypeConverters(val gson: Gson) {
    @TypeConverter fun imageListFromString(value: String): List<AlbumEntity.Image>? {
        return gson.fromJson(value, object : TypeToken<List<AlbumEntity.Image>?>() {}.type)
    }
    @TypeConverter fun stringFromImageList(list: List<AlbumEntity.Image>?): String {
        return gson.toJson(list)
    }
    @TypeConverter fun trackListFromString(value: String): List<AlbumEntity.Track>? {
        return gson.fromJson(value, object : TypeToken<List<AlbumEntity.Track>?>() {}.type)
    }
    @TypeConverter fun stringFromTrackList(list: List<AlbumEntity.Track>?): String {
        return gson.toJson(list)
    }
}