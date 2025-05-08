package com.example.geeksgame.model

import com.google.gson.annotations.SerializedName

data class Player(
    val id: String = "",
    val name: String = "",
    val phoneNum: String = "",
    val score: Int = 0
)
