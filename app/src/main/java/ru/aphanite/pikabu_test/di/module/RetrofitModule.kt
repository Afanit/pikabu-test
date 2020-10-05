package ru.aphanite.pikabu_test.di.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.aphanite.pikabu_test.di.scope.AppScope
import ru.aphanite.pikabu_test.network.PikabuApi

@Module
class RetrofitModule(private val baseUrl: String) {

    @Provides
    @AppScope
    fun provideRetrofit(gson: Gson): Retrofit {
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