package ru.aphanite.pikabu_test.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StoryConverters {
    private val gson = Gson()

    private val listType = object : TypeToken<List<String>>() {}.type

    @TypeConverter
    fun listFromString(value: String?): List<String>? {
        if (value == null) {
            return null
        }
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun listToString(list: List<String>?): String? {
        if (list == null) {
            return null
        }
        return gson.toJson(list)
    }
}