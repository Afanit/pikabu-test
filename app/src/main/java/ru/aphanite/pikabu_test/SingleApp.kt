package ru.aphanite.pikabu_test

import android.app.Application
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.aphanite.pikabu_test.data.StoryDto
import ru.aphanite.pikabu_test.di.component.AppComponent
import ru.aphanite.pikabu_test.di.component.DaggerAppComponent
import ru.aphanite.pikabu_test.di.module.GsonModule
import ru.aphanite.pikabu_test.di.module.RetrofitModule
import ru.aphanite.pikabu_test.network.PikabuApi
import javax.inject.Inject

class SingleApp : Application() {

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var pikabuApi: PikabuApi

    override fun onCreate() {
        super.onCreate()

        val BASE_URL = "https://pikabu.ru/page/interview/mobile-app/test-api/"

        appComponent = DaggerAppComponent.builder()
            .gsonModule(GsonModule())
            .retrofitModule(RetrofitModule(BASE_URL))
            .appContext(applicationContext)
            .build()

        appComponent.inject(this)

        val request = pikabuApi.getAllStories().enqueue(object : Callback<List<StoryDto>> {
            override fun onResponse(call: Call<List<StoryDto>>, response: Response<List<StoryDto>>) {
                Log.d("TAG", response.body().toString())
            }

            override fun onFailure(call: Call<List<StoryDto>>, t: Throwable) {
                Log.e("TAG", "Messgae", t)
            }
        })

        val request2 = pikabuApi.getStoryById(1).enqueue(object : Callback<StoryDto> {
            override fun onResponse(call: Call<StoryDto>, response: Response<StoryDto>) {
                Log.d("TAG", response.body().toString())
            }

            override fun onFailure(call: Call<StoryDto>, t: Throwable) {
                Log.e("TAG", "Messgae", t)
            }
        })
    }
}