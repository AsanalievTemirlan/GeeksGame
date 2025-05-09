package com.example.geeksgame.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.geeksgame.ui.navigation.Route
import com.example.geeksgame.ui.theme.Black
import com.example.geeksgame.ui.theme.YellowExtra
import com.example.geeksgame.ui.theme.customFontFamily

@Composable
fun GameOverScreen(navController: NavController, score: Int) {

    BackHandler(enabled = true) {
        navController.navigate(Route.MAIN)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Text(text = "НАБРАНО ОЧКОВ", fontSize = 20.sp, color = Color.White)
            Spa()
            Text(text = score.toString(), fontSize = 40.sp, color = Color.White)
            Spa(60.dp)
            Button(
                onClick = { navController.navigate(Route.GAME) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = YellowExtra
                ),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = "НАЧАТЬ ЗАНОВО",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = customFontFamily
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { navController.navigate(Route.MAIN) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = YellowExtra
                ),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = "МЕНЮ",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = customFontFamily
                )
            }

        }
    }
}