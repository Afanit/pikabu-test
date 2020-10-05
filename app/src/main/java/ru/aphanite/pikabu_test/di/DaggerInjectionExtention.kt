package ru.aphanite.pikabu_test.di

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.aphanite.pikabu_test.ui.activity.SingleActivity
import ru.aphanite.pikabu_test.SingleApp
import ru.aphanite.pikabu_test.di.component.ActivityComponent
import ru.aphanite.pikabu_test.di.component.AppComponent

fun Fragment.activityComponent(): ActivityComponent {
    return (requireActivity() as SingleActivity).activityComponent
}

fun AppCompatActivity.appComponent(): AppComponent {
    return (application as SingleApp).appComponent
}