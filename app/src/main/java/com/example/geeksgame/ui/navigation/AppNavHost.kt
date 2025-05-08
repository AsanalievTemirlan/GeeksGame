package com.example.geeksgame.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.geeksgame.R
import com.example.geeksgame.ui.screen.GameOverScreen
import com.example.geeksgame.ui.screen.GameScreen
import com.example.geeksgame.ui.screen.HomeScreen
import com.example.geeksgame.ui.screen.LoginScreen
import com.example.geeksgame.ui.screen.RegistrationScreen
import com.example.geeksgame.ui.screen.SetSystemBarsColor
import com.example.geeksgame.ui.screen.SplashScreen
import com.example.geeksgame.ui.screen.TopListScreen
import com.example.geeksgame.ui.theme.Black
import com.example.geeksgame.ui.theme.Gray1

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val startDestination = Route.LOGIN
    SetSystemBarsColor(Black, darkIcons = false)

    val bottomNavScreens = listOf(
        Route.MAIN,
        Route.TOP_LIST
    )

    // Define the list of navigation items
    val items = listOf(
        ScreensBottom.Home,
        ScreensBottom.Top
    )

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomNavScreens) {
                NavigationBar(
                    containerColor = Gray1,
                    contentColor = Color.White,
                    tonalElevation = 0.dp,
                    modifier = Modifier
                        .background(Black)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomEnd = 12.dp, bottomStart = 12.dp))

                ) {
                    items.forEach { screen ->
                        NavigationBarItem(
                            selected = currentRoute == screen.route,
                            onClick = {
                                if (currentRoute != screen.route) {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = screen.iconRes),
                                    contentDescription = screen.title,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = screen.title,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (currentRoute == screen.route) Color.Yellow else Color.White
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.Yellow,
                                unselectedIconColor = Color.White,
                                selectedTextColor = Color.Yellow,
                                unselectedTextColor = Color.White,
                                indicatorColor = Color.Transparent // Убирает фон-подсветку у выбранной иконки
                            )
                        )
                    }
                }
            }
        }
    )
    { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Route.MAIN) { HomeScreen(navController) }
            composable(Route.TOP_LIST) { TopListScreen(navController) }
            composable(Route.GAME) { GameScreen(navController) }
            composable(Route.SPLASH) { SplashScreen(navController) }
            composable(Route.LOGIN) { LoginScreen(navController) }
            composable(
                route = Route.OVER,
                arguments = listOf(navArgument("score") { type = NavType.IntType })
            ) { backStackEntry ->
                val score = backStackEntry.arguments?.getInt("score")
                GameOverScreen(navController, score?: 0) }
            composable(Route.REGISTRATION) { RegistrationScreen(navController) }
        }
    }
}


object Route {
    const val GAME = "game"
    const val OVER = "over/{score}"
    const val MAIN = "home"
    const val REGISTRATION = "registration"
    const val TOP_LIST = "list"
    const val SPLASH = "splash"
    const val LOGIN = "login"
}

sealed class ScreensBottom(val route: String, val title: String, val iconRes: Int) {
    data object Home : ScreensBottom(Route.MAIN, "Home", R.drawable.ic_logo)
    data object Top : ScreensBottom(Route.TOP_LIST, "Top", R.drawable.ic_top)
}