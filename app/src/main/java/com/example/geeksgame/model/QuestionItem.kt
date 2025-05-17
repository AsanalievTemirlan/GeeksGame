package com.example.geeksgame.model


import com.google.gson.annotations.SerializedName

data class QuestionItem(
    @SerializedName("answers")
    val answers: List<String>,
    @SerializedName("category")
    val category: String,
    @SerializedName("correctAnswer")
    val correctAnswer: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imageQuestion")
    val imageQuestion: String,
    @SerializedName("question")
    val question: String
)