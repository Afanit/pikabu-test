package ru.aphanite.pikabu_test.ui.story.listener

import ru.aphanite.pikabu_test.database.StoryRepository
import ru.aphanite.pikabu_test.model.StoryModel
import java.util.concurrent.ExecutorService
import javax.inject.Inject

class OnFavoriteClickListenerImpl @Inject constructor(private val repository: StoryRepository, private val executor: ExecutorService) :
    OnFavoriteClickListener {

    override fun onAction(data: StoryModel) {
        executor.execute {
            repository.updateFavorite(data)
        }
    }
}