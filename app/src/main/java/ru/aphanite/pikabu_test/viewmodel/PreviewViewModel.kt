package ru.aphanite.pikabu_test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.aphanite.pikabu_test.database.entity.StoryEntity
import ru.aphanite.pikabu_test.database.StoryRepository
import ru.aphanite.pikabu_test.network.PikabuApi
import ru.aphanite.pikabu_test.network.StoryDto
import ru.aphanite.pikabu_test.ui.story.listener.OnFavoriteClickListener
import ru.aphanite.pikabu_test.utils.Event
import java.util.concurrent.ExecutorService
import javax.inject.Inject

class PreviewViewModel @Inject constructor(
    val story: LiveData<StoryEntity>,
    val favoriteListener: OnFavoriteClickListener,
    private val pikabuApi: PikabuApi,
    private val repository: StoryRepository,
    private val executor: ExecutorService
) : ViewModel() {

    private val _updateResult = MutableLiveData<Event<Boolean>>()
    val updateResult: LiveData<Event<Boolean>> = _updateResult

    fun updateStory(id: Long) {
        pikabuApi.getStoryById(id).enqueue(object : Callback<StoryDto> {
            override fun onResponse(call: Call<StoryDto>, response: Response<StoryDto>) {
                val story = response.body()
                if (story == null) {
                    _updateResult.postValue(Event(true))
                    return
                }

                executor.execute {
                    repository.updateStory(story)
                    _updateResult.postValue(Event(true))
                }
            }

            override fun onFailure(call: Call<StoryDto>, t: Throwable) {
                _updateResult.value = Event(false)
            }
        })
    }
}