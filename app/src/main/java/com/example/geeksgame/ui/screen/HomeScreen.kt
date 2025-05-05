package com.example.geeksgame.ui.screen

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.geeksgame.ui.navigation.Route.GAME
import okhttp3.*
import java.io.IOException

@Composable
fun HomeScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Button(
            onClick = {
                sendLeadToBitrix(
                    onSuccess = {
                        navController.navigate(GAME)
                    },
                    onError = { error ->
                        Log.e("BitrixAPI", "Ошибка: $error")
                    }
                )
            },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(text = "Отправить лид", color = Color.White)
        }
    }
}

fun sendLeadToBitrix(onSuccess: () -> Unit, onError: (String) -> Unit) {
    val client = OkHttpClient()

    val requestBody = FormBody.Builder()
        .add("fields[TITLE]", "Новый лид")
        .add("fields[NAME]", "Имран")
        .add("fields[PHONE][0][VALUE]", "+996707123456")
        .add("fields[PHONE][0][VALUE_TYPE]", "MOBILE")
        .build()

    val request = Request.Builder()
        .url("https://b24-vlyubx.bitrix24.ru/rest/1/hwpp22kyp9su3ptj/crm.lead.add.json")
        .post(requestBody)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            onError(e.message ?: "Ошибка сети")
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                // Переход на главный поток для навигации
                Handler(Looper.getMainLooper()).post {
                    onSuccess()
                }
            } else {
                onError("HTTP ${response.code}")
            }
        }
    })
}
