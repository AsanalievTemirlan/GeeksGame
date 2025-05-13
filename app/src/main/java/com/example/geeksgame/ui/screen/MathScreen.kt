package com.example.geeksgame.ui.screen

import MathFormulaView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.geeksgame.ui.theme.Black
import kotlinx.coroutines.delay

@Composable
fun MathScreen(navController: NavController) {
    var selectedOption by remember { mutableStateOf("") }
    var timeLeft by remember { mutableStateOf(30 * 60) } // 30 минут в секундах

    val options = listOf(
        "А",
        "Б",
        "В",
        "Г"
    )

    // Пример выражений для сравнения
    val expression1 = "\\frac{1}{2} - \\frac{1}{2}"
    //val expression1 = "\\frac{\\sqrt{x^2 + 1}}{\\frac{1}{2}x}"
    val expression2 = "1"


    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000)
            timeLeft--
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .background(Black)
        ) {
            Text("Сравните значения:", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(8.dp))

            // Таймер
            Text(
                text = "Осталось времени: ${timeLeft / 60}:${
                    (timeLeft % 60).toString().padStart(2, '0')
                }",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Red
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Две рамки с выражениями
            // Две рамки с выражениями
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .border(1.dp, Color.White)
                        .padding(8.dp)
                ) {
                    MathFormulaView(
                        expression1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp) // Ограничим высоту
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .border(1.dp, Color.White)
                        .padding(8.dp)
                ) {
                    MathFormulaView(
                        expression2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                    )// Ограничим высоту)
                }
            }


            Spacer(modifier = Modifier.height(24.dp))

            // Варианты ответа (А, Б, В, Г)
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (option == selectedOption),
                            onClick = { selectedOption = option },
                            role = Role.RadioButton
                        )
                        .padding(8.dp)
                ) {
                    RadioButton(
                        selected = (option == selectedOption),
                        onClick = { selectedOption = option }
                    )
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedOption.isNotEmpty()) {
                Text("Вы выбрали: $selectedOption", fontWeight = FontWeight.Bold)
            }
        }
    }
}