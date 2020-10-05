package ru.aphanite.pikabu_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.aphanite.pikabu_test.di.component.ActivityComponent
import ru.aphanite.pikabu_test.ui.main.FeedFragment
import ru.aphanite.pikabu_test.ui.main.appComponent

class SingleActivity : AppCompatActivity() {

    lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        activityComponent = appComponent().activityComponentBuilder()
            .activityContext(this)
            .build()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FeedFragment())
                .commitNow()
        }
    }
}