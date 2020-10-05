package ru.aphanite.pikabu_test.di.module

import android.content.Context
import android.view.WindowManager
import dagger.Module
import dagger.Provides
import ru.aphanite.pikabu_test.di.qualifier.AppContext
import ru.aphanite.pikabu_test.di.scope.AppScope
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Module
class AppModule {
    @AppScope
    @Provides
    fun provideWindowManager(@AppContext context: Context): WindowManager {
        return context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    @AppScope
    @Provides
    fun provideExecutorService(): ExecutorService {
        return Executors.newCachedThreadPool()
    }
}