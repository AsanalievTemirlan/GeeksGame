package com.example.geeksgame.model

data class Player(
    val id: String = "",
    val name: String = "",
    val phoneNum: String = "",
    val score: Int = 0
)

data class Model(
    val id: Long,
    val question: String? = null,
    val answers: List<String>,
    val correctAnswer: String,
    val category: String,
    val imageQuestion: String? = null
)