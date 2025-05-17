package com.example.geeksgame.ui.screen.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.geeksgame.model.QuestionItem
import com.example.geeksgame.model.ResultItem
import com.example.geeksgame.model.loadJSONFromAsset
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.reflect.Type

class MathViewModel(application: Application) : AndroidViewModel(application) {

    private val _questionList = MutableStateFlow<List<QuestionItem>>(emptyList())
    val questionList: StateFlow<List<QuestionItem>> = _questionList.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    private val _timeLeft = MutableStateFlow(30 * 60)
    val timeLeft: StateFlow<Int> = _timeLeft.asStateFlow()

    private val _selectedAnswers = MutableStateFlow<Map<Int, String>>(emptyMap())
    val selectedAnswers: StateFlow<Map<Int, String>> = _selectedAnswers.asStateFlow()

    init {
        loadQuestions()
        startTimer()
    }

    private fun loadQuestions() {
        val context = getApplication<Application>()
        val json = loadJSONFromAsset(context, "full_questions.json")
        val listType: Type = object : TypeToken<List<QuestionItem>>() {}.type
        _questionList.value = Gson().fromJson(json, listType)
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (_timeLeft.value > 0) {
                delay(1000)
                _timeLeft.value = _timeLeft.value - 1
            }
        }
    }

    fun selectAnswer(index: Int, answer: String) {
        _selectedAnswers.value = _selectedAnswers.value.toMutableMap().apply {
            this[index] = answer
        }
    }

    fun goToNextQuestion() {
        if (_currentIndex.value < _questionList.value.size - 1) {
            _currentIndex.value++
        }
    }

    fun goToPreviousQuestion() {
        if (_currentIndex.value > 0) {
            _currentIndex.value--
        }
    }

    fun getResults(): String {
        val results = _questionList.value.mapIndexed { index, question ->
            val userAnswer = _selectedAnswers.value[index] ?: ""
            ResultItem(
                questionId = question.id,
                userAnswer = userAnswer,
                correctAnswer = question.correctAnswer,
                isCorrect = userAnswer == question.correctAnswer
            )
        }
        return Gson().toJson(results)
    }
}
