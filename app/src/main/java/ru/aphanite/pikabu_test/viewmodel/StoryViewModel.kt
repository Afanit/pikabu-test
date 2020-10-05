package ru.aphanite.pikabu_test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.aphanite.pikabu_test.database.entity.StoryEntity
import ru.aphanite.pikabu_test.database.StoryRepository
import ru.aphanite.pikabu_test.di.qualifier.EmptyMessage
import ru.aphanite.pikabu_test.di.qualifier.ScrollOnUpdate
import ru.aphanite.pikabu_test.network.PikabuApi
import ru.aphanite.pikabu_test.network.StoryDto
import ru.aphanite.pikabu_test.utils.Event
import java.util.concurrent.ExecutorService
import javax.inject.Inject

class StoryViewModel @Inject constructor(
    val storyList: LiveData<List<StoryEntity>>,
    @ScrollOnUpdate val scrollOnUpdate: Boolean,
    @EmptyMessage val emptyMessage: Int,
    private val pikabuApi: PikabuApi,
    private val repository: StoryRepository,
    private val executor: ExecutorService
) : ViewModel() {
    private val _updateResult = MutableLiveData<Event<Boolean>>()
    val updateResult: LiveData<Event<Boolean>> = _updateResult

    fun updateFeed() {
        pikabuApi.getAllStories().enqueue(object : Callback<List<StoryDto>> {
            override fun onResponse(
                call: Call<List<StoryDto>>,
                response: Response<List<StoryDto>>
            ) {
                val storyList = response.body()
                if (storyList == null) {
                    _updateResult.postValue(Event(true))
                    return
                }

                executor.execute {
                    repository.saveAllStory(storyList)
                    _updateResult.postValue(Event(true))
                }
            }

            override fun onFailure(call: Call<List<StoryDto>>, t: Throwable) {
                _updateResult.value = Event(false)
            }
        })
    }
}