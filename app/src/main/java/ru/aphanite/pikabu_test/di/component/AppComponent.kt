package ru.aphanite.pikabu_test.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.aphanite.pikabu_test.SingleApp
import ru.aphanite.pikabu_test.database.StoryRepository
import ru.aphanite.pikabu_test.di.module.AppModule
import ru.aphanite.pikabu_test.di.scope.AppScope
import ru.aphanite.pikabu_test.di.module.RetrofitModule
import ru.aphanite.pikabu_test.di.module.RoomModule
import ru.aphanite.pikabu_test.di.qualifier.AppContext

@AppScope
@Component(modules = [RetrofitModule::class, RoomModule::class, AppModule::class])
interface AppComponent {

    fun inject(app: SingleApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(@AppContext context: Context): Builder

        fun retrofitModule(module: RetrofitModule): Builder
        fun appModule(module: AppModule): Builder

        fun build(): AppComponent
    }

    @AppScope
    fun storyRepository(): StoryRepository

    fun activityComponentBuilder() : ActivityComponent.Builder
}