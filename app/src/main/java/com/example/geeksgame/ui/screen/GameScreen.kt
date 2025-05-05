package com.example.geeksgame.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.geeksgame.ui.navigation.Route.OVER

@Composable
fun GameScreen(navController: NavController) {
    val viewModel: MathGameViewModel = viewModel()
    val timeLeft by viewModel.timeLeft
    val expression by viewModel.expression
    val answers by viewModel.answers
    val selectedAnswer by viewModel.selectedAnswer
    val score by viewModel.score
    val isGameRunning by viewModel.isGameRunning

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Timer
        Text(
            text = String.format("%02d:%02d", timeLeft / 60, timeLeft % 60),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.padding(16.dp))

        // Expression
        Text(
            text = expression,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.padding(8.dp))

        // Answer Input Area (Placeholder)
        Text(
            text = score.toString(),
            fontSize = 20.sp,
            color = Color.White
        )
        Text(
            text = if (selectedAnswer != null) selectedAnswer!! else "",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.padding(16.dp))

        // Answer Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AnswerCard(value = answers.getOrNull(0) ?: "", viewModel, selectedAnswer)
            AnswerCard(value = answers.getOrNull(1) ?: "", viewModel, selectedAnswer)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AnswerCard(value = answers.getOrNull(2) ?: "", viewModel, selectedAnswer)
            AnswerCard(value = answers.getOrNull(3) ?: "", viewModel, selectedAnswer)
        }

        if (timeLeft == 0){
            navController.navigate(OVER)
        }
    }
}

@Composable
fun AnswerCard(value: String, viewModel: MathGameViewModel, selectedAnswer: String?) {
    val backgroundColor = when {
        selectedAnswer == value && value == viewModel.correctAnswerValue.value -> Color.Green
        selectedAnswer == value && value != viewModel.correctAnswerValue.value -> Color.Red
        else -> Color.White
    }

    Card(
        modifier = Modifier
            .size(80.dp)
            .clickable {
                if (selectedAnswer == null) viewModel.selectAnswer(value)
            }
            .border(2.dp, Color.Black),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}