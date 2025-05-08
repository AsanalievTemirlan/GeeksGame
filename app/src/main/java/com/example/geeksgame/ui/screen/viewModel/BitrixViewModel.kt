package com.example.geeksgame.ui.screen.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class BitrixViewModel : ViewModel() {

    private val client = OkHttpClient()
    private val gson = Gson()

    private val _sendResult = MutableLiveData<Boolean?>()  // Положительное или отрицательное состояние регистрации
    val sendResult: LiveData<Boolean?> = _sendResult


    // Метод для отправки лида в Bitrix
    fun sendLeadToBitrix(name: String, phone: String) {
        val url = "https://geektech.bitrix24.ru/rest/1/e08w1jvst0jj152c/crm.lead.add.json" +
                "?fields[SOURCE_ID]=127" +
                "&fields[NAME]=$name" +
                "&fields[TITLE]=GEEKS GAME: Хакатон 2025" +
                "&fields[PHONE][0][VALUE]=$phone" +
                "&fields[PHONE][0][VALUE_TYPE]=WORK"

        val request = Request.Builder()
            .url(url)
            .post("".toRequestBody()) // пустое тело, но POST
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                _sendResult.postValue(false)  // Если ошибка, помечаем как неудачу
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    _sendResult.postValue(true)
                    Log.e("ololo", "onResponse: send successful", )// Если успешный ответ
                } else {
                    _sendResult.postValue(false)  // Если ошибка на сервере
                }
            }
        })
    }
}
