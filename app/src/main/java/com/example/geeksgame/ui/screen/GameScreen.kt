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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.geeksgame.ui.navigation.Route.OVER
import com.example.geeksgame.ui.theme.Black
import com.example.geeksgame.ui.theme.GreenExtra
import com.example.geeksgame.ui.theme.PinkExtra
import com.example.geeksgame.ui.theme.YellowExtra

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
            .background(Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoImg(size = 100.dp, top = 100.dp)

        Spa()

        // Timer
        Text(
            text = String.format("%02d:%02d", timeLeft / 60, timeLeft % 60),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = if (timeLeft <= 5) Color.Red else YellowExtra
        )

        Spa()


        // Expression Card
        Card(
            modifier = Modifier
                .size(height = 120.dp, width = 300.dp)
                .border(2.dp, Color.White, RoundedCornerShape(8.dp)),
            colors = CardDefaults.cardColors(containerColor = Black)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text(
                    text = expression,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))

        // Score
        Text(
            text = "OЧКОВ $score",
            fontSize = 20.sp,
            color = Color.White
        )

        Spa()

        // Answer Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AnswerCard(value = answers.getOrNull(0) ?: "", viewModel, selectedAnswer)
            AnswerCard(value = answers.getOrNull(1) ?: "", viewModel, selectedAnswer)
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AnswerCard(value = answers.getOrNull(2) ?: "", viewModel, selectedAnswer)
            AnswerCard(value = answers.getOrNull(3) ?: "", viewModel, selectedAnswer)
        }

        LaunchedEffect(timeLeft) {
            if (timeLeft <= 0) {
                navController.navigate("over/$score") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        }

    }
}

@Composable
fun AnswerCard(value: String, viewModel: MathGameViewModel, selectedAnswer: String?) {
    val backgroundColor = when {
        selectedAnswer == value && value == viewModel.correctAnswerValue.value -> GreenExtra
        selectedAnswer == value && value != viewModel.correctAnswerValue.value -> PinkExtra
        else -> Color.White
    }

    Card(
        modifier = Modifier
            .size(120.dp)
            .clickable {
                if (selectedAnswer == null) viewModel.selectAnswer(value)
            }
            .border(2.dp, Color.Black, RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}