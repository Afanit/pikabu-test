package ru.aphanite.pikabu_test.di.module.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.Module
import dagger.Provides
import ru.aphanite.pikabu_test.R
import ru.aphanite.pikabu_test.database.entity.StoryEntity
import ru.aphanite.pikabu_test.database.StoryRepository
import ru.aphanite.pikabu_test.di.qualifier.EmptyMessage
import ru.aphanite.pikabu_test.di.qualifier.ScrollOnUpdate
import ru.aphanite.pikabu_test.di.scope.FragmentScope

@Module(includes = [CommonFragmentModule::class])
class FeedModule {
    @FragmentScope
    @ScrollOnUpdate
    @Provides
    fun provideScrollOnUpdate(): Boolean {
        return false
    }

    @FragmentScope
    @EmptyMessage
    @Provides
    fun provideEmptyMessage(): Int {
        return R.string.no_stories
    }

    @FragmentScope
    @Provides
    fun provideStoryList(repository: StoryRepository): LiveData<List<StoryEntity>> {
        return repository.getAllStory()
    }

    @FragmentScope
    @Provides
    fun provideStory(): LiveData<StoryEntity> {
        return MutableLiveData()
    }
}