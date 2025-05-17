package com.example.geeksgame.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.geeksgame.model.ResultItem
import com.example.geeksgame.ui.navigation.Route
import com.example.geeksgame.ui.theme.Black
import com.example.geeksgame.ui.theme.GreenExtra
import com.example.geeksgame.ui.theme.PinkExtra
import com.example.geeksgame.ui.theme.Yellow
import com.example.geeksgame.ui.theme.YellowExtra
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Composable
fun ResultScreen(jsonResults: String?, navController: NavController) {
    val results = remember(jsonResults) {
        val type = object : TypeToken<List<ResultItem>>() {}.type
        Gson().fromJson<List<ResultItem>>(jsonResults, type)
    }

    Scaffold(
        containerColor = Black,
        bottomBar = {
            Button(
                onClick = { navController.navigate(Route.MAIN) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = YellowExtra),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(text = "На главную")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Результаты теста",
                fontSize = 24.sp,
                color = Yellow,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn {
                items(results ?: emptyList()) { result ->
                    Text(
                        text = "Задание ${result.questionId}: " +
                                if (result.isCorrect) "Верно" else "Неверно" +
                                        " (Ваш ответ: ${result.userAnswer.ifEmpty { "нет ответа" }}, " +
                                        "Правильный: ${result.correctAnswer})",
                        color = if (result.isCorrect) GreenExtra else PinkExtra,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}