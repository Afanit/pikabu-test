package ru.aphanite.pikabu_test.ui.main

import androidx.lifecycle.ViewModel
import androidx.paging.toLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.aphanite.pikabu_test.data.StoryDto
import ru.aphanite.pikabu_test.data.StoryDao
import ru.aphanite.pikabu_test.data.StoryEntity
import ru.aphanite.pikabu_test.network.PikabuApi
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    val feedAdapter: FeedAdapter,
    private val pikabuApi: PikabuApi,
    private val storyDao: StoryDao
) : ViewModel() {

    val storyList = storyDao.getAllStory()

    fun updateFeed(listener: OnResultListener) {
        pikabuApi.getAllStories().enqueue(object : Callback<List<StoryDto>> {
            override fun onResponse(
                call: Call<List<StoryDto>>,
                response: Response<List<StoryDto>>
            ) {
                val storyList = mutableListOf<StoryEntity>()

                response.body()?.map {
                    storyList += StoryEntity(it.id, it.title, it.body, it.urls, false)
                }

                Thread {
                    storyDao.insertAllStory(storyList)
                    listener.onSuccess()
                }.start()

                // TODO подписаться на данные из рума
            }

            override fun onFailure(call: Call<List<StoryDto>>, t: Throwable) {
                listener.onFailure()
            }
        })
    }

    interface OnResultListener {
        fun onSuccess()
        fun onFailure()
    }
}