package com.example.geeksgame.ui.screen

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.geeksgame.R
import com.example.geeksgame.ui.navigation.Route
import com.example.geeksgame.ui.navigation.Route.GAME
import com.example.geeksgame.ui.theme.Black
import com.example.geeksgame.ui.theme.YellowExtra
import com.example.geeksgame.ui.theme.customFontFamily
import okhttp3.*
import java.io.IOException

@Composable
fun HomeScreen(navController: NavController) {

    Box(
        modifier = Modifier.fillMaxSize().background(Black),
    ) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 30.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(R.drawable.geeks_txt), contentDescription = null)
        Spa(50.dp)
        Button(
            onClick = {
                /* регистрация */
                navController.navigate(Route.GAME)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = YellowExtra
            ),
            shape = RoundedCornerShape(6.dp)
        ) {
            Text("START", color = Color.White, fontSize = 16.sp, fontFamily = customFontFamily)
        }
    }
}}