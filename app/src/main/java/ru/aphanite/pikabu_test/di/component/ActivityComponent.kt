package ru.aphanite.pikabu_test.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Subcomponent
import ru.aphanite.pikabu_test.di.scope.ActivityScope
import ru.aphanite.pikabu_test.di.module.ViewModelModule
import ru.aphanite.pikabu_test.di.qualifier.ActivityContext

@ActivityScope
@Subcomponent(modules = [ViewModelModule::class])
interface ActivityComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun activityContext(@ActivityContext context: Context): Builder

        fun build(): ActivityComponent
    }

    fun feedComponentBuilder(): FeedComponent.Builder
}