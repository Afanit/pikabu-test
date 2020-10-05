package ru.aphanite.pikabu_test.di.module

import android.content.Context
import android.util.Size
import android.view.WindowManager
import dagger.Module
import dagger.Provides
import ru.aphanite.pikabu_test.di.qualifier.AppContext
import ru.aphanite.pikabu_test.di.scope.AppScope
import ru.aphanite.pikabu_test.di.scope.FragmentScope
import ru.aphanite.pikabu_test.ui.main.FeedAdapter

@Module
class AppModule {

    @AppScope
    @Provides
    fun provideWindowManager(@AppContext context: Context): WindowManager {
        return context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }
}