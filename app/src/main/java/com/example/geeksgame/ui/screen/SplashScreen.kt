package com.example.geeksgame.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.geeksgame.ui.navigation.Route
import com.example.geeksgame.ui.theme.Black
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    val userPrefs = UserPrefs(context)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black),
        contentAlignment = Alignment.Center
    ) {
        LogoImg(size = 200.dp)
    }

    LaunchedEffect(key1 = true) {
        delay(2000L) // 3 секунды
        navController.popBackStack()
        if (userPrefs.isRegistered()){
            navController.navigate(Route.LOGIN)
        }
        else{
            navController.navigate(Route.MAIN)
        }
    }
}