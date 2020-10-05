package ru.aphanite.pikabu_test.utils

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import ru.aphanite.pikabu_test.R
import ru.aphanite.pikabu_test.di.qualifier.ActivityContext
import ru.aphanite.pikabu_test.di.scope.ActivityScope
import javax.inject.Inject

@ActivityScope
class ColorConst @Inject constructor(@ActivityContext context: Context) {
    val dark = ResourcesCompat.getColor(context.resources, R.color.colorDark, context.theme)
    val black = ResourcesCompat.getColor(context.resources, R.color.colorBlack, context.theme)
    val orange = ResourcesCompat.getColor(context.resources, R.color.colorOrange, context.theme)
    val white = ResourcesCompat.getColor(context.resources, R.color.colorWhite, context.theme)
}