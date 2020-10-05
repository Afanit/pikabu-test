package ru.aphanite.pikabu_test.ui.main

import ru.aphanite.pikabu_test.data.StoryDao
import ru.aphanite.pikabu_test.data.StoryEntityFavorite
import ru.aphanite.pikabu_test.data.StoryModel
import ru.aphanite.pikabu_test.di.scope.FragmentScope
import javax.inject.Inject

@FragmentScope
class FeedOnFavoriteClickListener @Inject constructor(private val dao: StoryDao) : OnFavoriteButtonClickListener {

    override fun onAction(data: StoryModel) {
        Thread { dao.updateFavorite(StoryEntityFavorite(data.id, data.isFavorite)) }.start()
    }
}