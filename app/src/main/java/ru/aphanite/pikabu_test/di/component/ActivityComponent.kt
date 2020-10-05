package ru.aphanite.pikabu_test.di.component

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.aphanite.pikabu_test.ui.activity.SingleActivity
import ru.aphanite.pikabu_test.di.scope.ActivityScope
import ru.aphanite.pikabu_test.di.module.ViewModelModule
import ru.aphanite.pikabu_test.di.qualifier.ActivityContext
import ru.aphanite.pikabu_test.ui.activity.FragmentPagerAdapter
import ru.aphanite.pikabu_test.utils.ColorConst

@ActivityScope
@Subcomponent(modules = [ViewModelModule::class])
interface ActivityComponent {

    fun inject(singleActivity: SingleActivity)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun activity(activity: AppCompatActivity): Builder
        @BindsInstance
        fun activityContext(@ActivityContext context: Context): Builder

        fun build(): ActivityComponent
    }

    @ActivityScope
    fun mainPagerAdapter(): FragmentPagerAdapter
    @ActivityScope
    fun colorConst(): ColorConst

    fun feedComponentBuilder(): FeedComponent.Builder
    fun favoriteComponentBuilder(): FavoriteComponent.Builder
    fun previewComponentBuilder(): PreviewComponent.Builder
}