package com.example.geeksgame.ui.screen

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.geeksgame.R

@Composable
fun LogoImg(
    top: Dp = 20.dp,
    bottom: Dp = 20.dp,
    size: Dp? = null
) {
    Image(
        painter = painterResource(R.drawable.ic_logo),
        contentDescription = null,
        modifier = Modifier
            .padding(top = top, bottom = bottom)
            .then(if (size != null) Modifier.size(size) else Modifier)
    )
}

@Composable
fun Spa(height: Dp = 20.dp) {
    Spacer(Modifier.height(height))

}


@Composable
fun SetSystemBarsColor(color: Color, darkIcons: Boolean = false) {
    val view = LocalView.current
    val context = LocalContext.current
    val activity = context.findActivity()

    if (!view.isInEditMode && activity != null) {
        val window = activity.window
        window.statusBarColor = color.toArgb()
        window.navigationBarColor = color.toArgb()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val controller = WindowInsetsControllerCompat(window, view)
        controller.isAppearanceLightStatusBars = darkIcons
        controller.isAppearanceLightNavigationBars = darkIcons
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is android.content.ContextWrapper -> baseContext.findActivity()
    else -> null
}

