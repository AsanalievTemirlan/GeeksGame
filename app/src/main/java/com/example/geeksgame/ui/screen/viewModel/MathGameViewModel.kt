package com.example.geeksgame.ui.screen.viewModel

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MathGameViewModel : ViewModel() {
    val timeLeft = mutableIntStateOf(10) // 1 minute in seconds
    val score = mutableIntStateOf(0)
    val expression = mutableStateOf("")
    val answers = mutableStateOf(listOf<String>())
    val selectedAnswer = mutableStateOf<String?>(null)
    val isGameRunning = mutableStateOf(false)
    val correctAnswerValue = mutableStateOf("")

    init {
        startNewGame()
    }

    private fun startNewGame() {
        isGameRunning.value = true
        timeLeft.value = 30
        score.value = 0
        selectedAnswer.value = null
        viewModelScope.launch {
            while (timeLeft.value > 0 && isGameRunning.value) {
                delay(1000)
                timeLeft.value--
                if (timeLeft.value == 0) endGame()
            }
        }
        generateQuestion()
    }

    private fun endGame() {
        isGameRunning.value = false
        selectedAnswer.value = null
    }
//    "+", "-", "*", "/", "%",
    private fun generateQuestion() {
        val num1 = Random.nextInt(1, 20)
        val num2 = Random.nextInt(1, 20)
        val operator = listOf( "<", ">", "=").random()
        val correctAnswer: String
        val result: Int

        when (operator) {
            "+" -> {
                result = num1 + num2
                correctAnswer = result.toString()
                expression.value = "$num1 + $num2"
            }

            "-" -> {
                result = num1 - num2
                correctAnswer = result.toString()
                expression.value = "$num1 - $num2"
            }

            "*" -> {
                result = num1 * num2
                correctAnswer = result.toString()
                expression.value = "$num1 * $num2"
            }

            "/" -> {
                result = num1 / num2
                correctAnswer = result.toString()
                expression.value = "$num1 / $num2"
            }

            "%" -> {
                result = num1 % num2
                correctAnswer = result.toString()
                expression.value = "$num1 % $num2"
            }

            "<", ">", "=" -> {
                correctAnswer = when {
                    num1 == num2 -> "="
                    num1 > num2 -> ">"
                    num1 < num2 -> "<"
                    else -> "?"
                }
                expression.value = "$num1  $num2"
            }

            else -> {
                result = num1 + num2
                correctAnswer = result.toString()
                expression.value = "$num1 + $num2"
            }
        }

        val wrongAnswers = generateWrongAnswers(correctAnswer, operator)
        answers.value = (listOf(correctAnswer) + wrongAnswers).shuffled()
        correctAnswerValue.value = correctAnswer
        selectedAnswer.value = null
    }

    private fun generateWrongAnswers(correctAnswer: String, operator: String): List<String> {
        return if (correctAnswer !in listOf("<", ">", "=")) {
            val baseValue = correctAnswer.toIntOrNull() ?: 0
            listOf(
                (baseValue - Random.nextInt(1, 5)).toString(),
                (baseValue + Random.nextInt(1, 5)).toString(),
                (baseValue + Random.nextInt(6, 10)).toString()
            ).distinct().filter { it != correctAnswer }
        } else {
            return listOf("<", ">", "?", "=").filter { it != correctAnswer }
        }
    }


    fun selectAnswer(answer: String) {
        selectedAnswer.value = answer
        if (answer == correctAnswerValue.value) {
            score.value += 1
            viewModelScope.launch {
                delay(500)
                generateQuestion()
            }
        } else {
            Log.e("ololo", "selectAnswer: $answer, ${correctAnswerValue.value}", )
            viewModelScope.launch {
                delay(500)
                selectedAnswer.value = null
            }
        }
    }
}