package com.vfurkana.caselastfm.data.service.local.model

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@ProvidedTypeConverter
class LastFMTypeConverters(val gson: Gson) {
    @TypeConverter fun imageListFromString(value: String): List<ImageEntity>? {
        return gson.fromJson(value, object : TypeToken<List<ImageEntity>?>() {}.type)
    }

    @TypeConverter fun stringFromImageList(list: List<ImageEntity>?): String {
        return gson.toJson(list)
    }

    @TypeConverter fun trackListFromString(value: String): List<TrackEntity>? {
        return gson.fromJson(value, object : TypeToken<List<TrackEntity>?>() {}.type)
    }

    @TypeConverter fun stringFromTrackList(list: List<TrackEntity>?): String {
        return gson.toJson(list)
    }

    @TypeConverter fun tagListFromString(value: String): List<TagEntity>? {
        return if (value.isNullOrEmpty()) null else {
            gson.fromJson(value, object : TypeToken<List<TagEntity>?>() {}.type)
        }
    }

    @TypeConverter fun stringFromTagList(list: List<TagEntity>?): String {
        return gson.toJson(list)
    }

    @TypeConverter fun wikiFromString(value: String): WikiEntity? {
        return gson.fromJson(value, WikiEntity::class.java)
    }

    @TypeConverter fun stringFromWiki(wikiEntity: WikiEntity?): String {
        return gson.toJson(wikiEntity)
    }

}