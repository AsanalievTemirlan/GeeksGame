package com.example.geeksgame.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.geeksgame.R
import com.example.geeksgame.ui.navigation.Route
import com.example.geeksgame.ui.navigation.Route.GAME
import com.example.geeksgame.ui.theme.Black
import com.example.geeksgame.ui.theme.GreenExtra
import com.example.geeksgame.ui.theme.YellowExtra
import com.example.geeksgame.ui.theme.customFontFamily

@Composable
fun HomeScreen(navController: NavController) {


    BackHandler(enabled = true) {

    }

    Box(
        modifier = Modifier.fillMaxSize().background(Black),
    ) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 30.dp).padding(top = 100.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(R.drawable.geeks_txt),
                contentDescription = null
            )
        }
        Spa(50.dp)
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clickable {
                    // Переход на другой экран
                    navController.navigate(Route.MATH)
                },
            elevation = CardDefaults.elevatedCardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = GreenExtra),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Математика часть 1",
                modifier = Modifier.padding(16.dp),
                fontSize = 18.sp
            )
        }
    }
}}