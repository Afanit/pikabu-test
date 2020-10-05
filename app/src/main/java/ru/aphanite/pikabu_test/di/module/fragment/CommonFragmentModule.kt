package ru.aphanite.pikabu_test.di.module.fragment

import android.R
import android.content.Context
import android.util.DisplayMetrics
import android.util.Size
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import ru.aphanite.pikabu_test.di.qualifier.ActivityContext
import ru.aphanite.pikabu_test.di.scope.FragmentScope
import ru.aphanite.pikabu_test.ui.story.listener.*

@Module
class CommonFragmentModule {
    @FragmentScope
    @Provides
    fun provideLinearLayoutManager(@ActivityContext context: Context): LinearLayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    @FragmentScope
    @Provides
    fun provideFavoriteListener(listener: OnFavoriteClickListenerImpl): OnFavoriteClickListener {
        return listener
    }

    @FragmentScope
    @Provides
    fun provideStoryListener(listener: OnStoryClickListenerImpl): OnStoryClickListener {
        return listener
    }

    @Provides
    fun provideDisplaySize(activity: AppCompatActivity, windowManager: WindowManager): Size {
        val actionBarHeight = activity.actionBar?.height ?: 0
        val contentTop = (activity.findViewById(R.id.content) as ViewGroup).top

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val finalHeight = displayMetrics.heightPixels - contentTop - actionBarHeight
        return Size(
            displayMetrics.widthPixels,
            finalHeight
        )
    }
}