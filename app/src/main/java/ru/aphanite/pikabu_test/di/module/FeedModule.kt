package ru.aphanite.pikabu_test.di.module

import android.content.Context
import android.util.DisplayMetrics
import android.util.Size
import android.view.WindowManager
import dagger.Module
import dagger.Provides
import ru.aphanite.pikabu_test.data.StoryDao
import ru.aphanite.pikabu_test.di.qualifier.AppContext
import ru.aphanite.pikabu_test.di.scope.FragmentScope
import ru.aphanite.pikabu_test.ui.main.FeedAdapter
import ru.aphanite.pikabu_test.ui.main.OnFavoriteButtonClickListener

@Module
class FeedModule {
    @Provides
    @FragmentScope
    fun provideDisplaySize(windowManager: WindowManager): Size {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return Size(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }
}

