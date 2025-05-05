package com.example.geeksgame.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.geeksgame.R
import com.example.geeksgame.ui.screen.GameOverScreen
import com.example.geeksgame.ui.screen.GameScreen
import com.example.geeksgame.ui.screen.HomeScreen
import com.example.geeksgame.ui.screen.RegistrationScreen
import com.example.geeksgame.ui.screen.TopListScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

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
                NavigationBar {
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
                                    contentDescription = screen.title
                                )
                            },
                            label = { Text(screen.title) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.MAIN,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Route.MAIN) { HomeScreen(navController) }
            composable(Route.TOP_LIST) { TopListScreen(navController) }
            composable(Route.GAME) { GameScreen(navController) }
            composable(Route.OVER) { GameOverScreen(navController) }
            composable(Route.REGISTRATION) { RegistrationScreen(navController) }
        }
    }
}

object Route {
    const val GAME = "game"
    const val OVER = "over"
    const val MAIN = "home"
    const val REGISTRATION = "registration"
    const val TOP_LIST = "list"
}

sealed class ScreensBottom(val route: String, val title: String, val iconRes: Int) {
    data object Home : ScreensBottom(Route.MAIN, "Home", R.drawable.home)
    data object Top : ScreensBottom(Route.TOP_LIST, "Top", R.drawable.home)
}