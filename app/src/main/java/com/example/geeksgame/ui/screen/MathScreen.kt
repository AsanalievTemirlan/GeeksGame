package com.example.geeksgame.ui.screen

import MathFormulaView
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.geeksgame.model.ResultItem
import com.example.geeksgame.model.loadJSONFromAsset
import com.example.geeksgame.model.QuestionItem
import com.example.geeksgame.ui.screen.viewModel.MathViewModel
import com.example.geeksgame.ui.theme.Black
import com.example.geeksgame.ui.theme.Gray2
import com.example.geeksgame.ui.theme.GreenExtra
import com.example.geeksgame.ui.theme.Yellow
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import java.lang.reflect.Type
import kotlin.collections.set

// MathScreen.kt

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MathScreen(navController: NavController) {
    val viewModel: MathViewModel = viewModel()
    val questionList by viewModel.questionList.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val timeLeft by viewModel.timeLeft.collectAsState()
    val selectedAnswers by viewModel.selectedAnswers.collectAsState()

    val scrollState = rememberScrollState()
    val options = listOf("A", "B", "C", "D")

    // Переход на экран результатов при окончании времени
    LaunchedEffect(timeLeft) {
        if (timeLeft == 0) {
            val resultsJson = viewModel.getResults()
            navController.navigate("result_screen/$resultsJson")
        }
    }

    Scaffold(
        containerColor = Black,
        bottomBar = {
            Button(
                onClick = {
                    val resultsJson = viewModel.getResults()
                    navController.navigate("result_screen/$resultsJson")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Gray2),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Завершить тест", fontSize = 18.sp, color = Yellow)
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState)
                .pointerInput(currentIndex) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        if (dragAmount > 50) viewModel.goToPreviousQuestion()
                        else if (dragAmount < -50) viewModel.goToNextQuestion()
                    }
                }
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ClickableText(
                    text = AnnotatedString("<"),
                    onClick = { viewModel.goToPreviousQuestion() },
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.White, fontSize = 24.sp)
                )
                Text(
                    text = "${timeLeft / 60}:${(timeLeft % 60).toString().padStart(2, '0')}",
                    color = Color.Red,
                    fontSize = 18.sp
                )
                ClickableText(
                    text = AnnotatedString(">"),
                    onClick = { viewModel.goToNextQuestion() },
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.White, fontSize = 24.sp)
                )
            }

            Spacer(Modifier.height(16.dp))

            AnimatedContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize() // <--- важно
                    .background(Black), // <--- фон, чтобы не было артефактов
                targetState = currentIndex,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally(animationSpec = tween(300)) { width -> width } togetherWith
                                slideOutHorizontally(animationSpec = tween(300)) { width -> -width }
                    } else {
                        (slideInHorizontally(animationSpec = tween(300)) { width -> -width }).togetherWith(
                            slideOutHorizontally(animationSpec = tween(300)) { width -> width })
                    }.using(SizeTransform(clip = true))
                }
            ) { index ->
                val question = questionList.getOrNull(index)
                question?.let {
                    Column {
                        Text("Задание ${it.id}", fontSize = 18.sp)

                        it.question?.takeIf { it.isNotBlank() }?.let { latex ->
                            Box(Modifier.fillMaxWidth()) {
                                MathFormulaView(latex)
                            }
                        }

                        it.imageQuestion?.takeIf { it.isNotBlank() }?.let { url ->
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current).data(url).crossfade(true).build(),
                                contentDescription = null,
                                modifier = Modifier.fillMaxWidth().padding(top = 0.dp).height(150.dp)
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                        if (it.answers.size >= 2) {
                            Row(
                                modifier = Modifier.fillMaxWidth().height(100.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                it.answers.take(2).forEach { ans ->
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.weight(1f).fillMaxHeight().border(1.dp, Color.White).padding(0.dp)
                                    ) {
                                        MathFormulaView(latex = ans)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            val selected = selectedAnswers[currentIndex] ?: ""
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (option == selected),
                            onClick = { viewModel.selectAnswer(currentIndex, option) },
                            role = Role.RadioButton
                        )
                        .padding(8.dp)
                ) {
                    RadioButton(
                        selected = (option == selected),
                        onClick = { viewModel.selectAnswer(currentIndex, option) },
                        colors = RadioButtonDefaults.colors(selectedColor = Yellow)
                    )
                    Text(option, Modifier.padding(start = 8.dp), color = Color.White)
                }
            }

            if (selected.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.goToNextQuestion() },
                    colors = ButtonDefaults.buttonColors(containerColor = GreenExtra),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text("Далее ->")
                }
            }
        }
    }
}
