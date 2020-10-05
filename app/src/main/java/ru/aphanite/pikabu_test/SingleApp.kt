package ru.aphanite.pikabu_test

import android.app.Application
import ru.aphanite.pikabu_test.di.component.AppComponent
import ru.aphanite.pikabu_test.di.component.DaggerAppComponent
import ru.aphanite.pikabu_test.di.module.RetrofitModule
import ru.aphanite.pikabu_test.network.PikabuApi
import javax.inject.Inject

class SingleApp : Application() {

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var pikabuApi: PikabuApi

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .retrofitModule(RetrofitModule())
            .appContext(applicationContext)
            .build()

        appComponent.inject(this)
    }
}