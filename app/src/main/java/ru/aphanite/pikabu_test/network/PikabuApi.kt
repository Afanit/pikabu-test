package ru.aphanite.pikabu_test.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.aphanite.pikabu_test.data.StoryDto


interface PikabuApi {

    @GET("feed.php")
    fun getAllStories(): Call<List<StoryDto>>

    @GET("story.php")
    fun getStoryById(@Query("id") id: Int): Call<StoryDto>
}