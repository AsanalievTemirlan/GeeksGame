package com.example.geeksgame.model

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets


data class Player(
    val id: String = "",
    val name: String = "",
    val phoneNum: String = "",
    val score: Int = 0
)


data class ResultItem(
    val questionId: Int,
    val userAnswer: String,
    val correctAnswer: String,
    val isCorrect: Boolean
)

fun loadJSONFromAsset(context: Context, filename: String): String? {
    var json: String? = null
    try {
        val `is`: InputStream = context.getAssets().open(filename)
        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        json = String(buffer, StandardCharsets.UTF_8)
    } catch (ex: IOException) {
        ex.printStackTrace()
    }
    return json
}
