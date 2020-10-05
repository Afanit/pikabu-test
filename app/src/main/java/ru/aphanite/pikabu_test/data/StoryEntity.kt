package ru.aphanite.pikabu_test.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit2.http.Field

@Entity(tableName = "story_entity")
data class StoryEntity(@PrimaryKey val id: Long, val title: String, val body: String?, val urls: List<String>?, val isFavorite: Boolean)