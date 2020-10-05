package ru.aphanite.pikabu_test.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.aphanite.pikabu_test.di.qualifier.BaseUrl
import ru.aphanite.pikabu_test.di.scope.AppScope
import ru.aphanite.pikabu_test.network.PikabuApi

@Module
class RetrofitModule {

    @BaseUrl
    @Provides
    @AppScope
    fun provideBaseUrl(): String {
        return "https://pikabu.ru/page/interview/mobile-app/test-api/"
    }

    @Provides
    @AppScope
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @AppScope
    fun provideRetrofit(gson: Gson, @BaseUrl baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @AppScope
    fun providePikabuApi(retrofit: Retrofit): PikabuApi {
        return retrofit.create(PikabuApi::class.java)
    }
}