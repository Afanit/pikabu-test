package ru.aphanite.pikabu_test.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.aphanite.pikabu_test.SingleApp
import ru.aphanite.pikabu_test.di.module.AppModule
import ru.aphanite.pikabu_test.di.scope.AppScope
import ru.aphanite.pikabu_test.di.module.GsonModule
import ru.aphanite.pikabu_test.di.module.RetrofitModule
import ru.aphanite.pikabu_test.di.module.RoomModule
import ru.aphanite.pikabu_test.di.qualifier.AppContext

@AppScope
@Component(modules = [GsonModule::class, RetrofitModule::class, RoomModule::class, AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun gsonModule(module: GsonModule): Builder

        fun retrofitModule(module: RetrofitModule): Builder
//        fun context(context: Context): AppComponent.Builder

        @BindsInstance
        fun appContext(@AppContext context: Context): Builder

        fun appModule(module: AppModule): Builder

        fun build(): AppComponent
    }

    fun inject(app: SingleApp)

    fun activityComponentBuilder() : ActivityComponent.Builder
}