package ru.aphanite.pikabu_test.network

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

/**
 * {
 *      "id": 2,
 *      "title": "А что если...",
 *      "body": "... у Древних Египтян была развитая письменность как наша, но они стали так зависимы от мемов, что стали просто использовать картинки для общения?",
 *      "images": []
 * }
 */
data class StoryDto(val id: Long, val title: String, val body: String?, @SerializedName("images") val urls: List<String>?)