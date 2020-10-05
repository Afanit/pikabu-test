package ru.aphanite.pikabu_test.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import ru.aphanite.pikabu_test.di.scope.AppScope

@Module
class GsonModule {

    @Provides
    @AppScope
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }
}