package com.example.geeksgame.ui.screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geeksgame.model.LeadListResponse
import com.example.geeksgame.model.LeadModel
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

    private val _leadList = MutableLiveData<List<LeadModel>>()
    val leadList: LiveData<List<LeadModel>> = _leadList

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
                    _sendResult.postValue(true)  // Если успешный ответ
                } else {
                    _sendResult.postValue(false)  // Если ошибка на сервере
                }
            }
        })
    }

    // Метод для получения всех лидов из Bitrix
    fun fetchLeadsFromBitrix() {
        val url = "https://geektech.bitrix24.ru/rest/1/e08w1jvst0jj152c/crm.lead.list.json"

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ololo", "Ошибка: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                val root = gson.fromJson(json, LeadListResponse::class.java)
                _leadList.postValue(root.result)
            }
        })
    }

    // Метод для обновления очков лида в Bitrix
    fun updateLeadPoints(leadId: String, points: Int) {
        // Здесь мы обновляем TITLE лида, добавляя количество очков
        val url = "https://geektech.bitrix24.ru/rest/1/e08w1jvst0jj152c/crm.lead.update.json" +
                "?id=$leadId" +
                "&fields[TITLE]=Очки: $points"  // Обновляем TITLE с новыми очками

        val request = Request.Builder()
            .url(url)
            .post("".toRequestBody()) // Пустое тело, но POST запрос для обновления
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("BitrixUpdate", "Ошибка обновления очков: ${e.message}")
                _sendResult.postValue(false)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d("BitrixUpdate", "Очки успешно обновлены")
                    _sendResult.postValue(true)
                } else {
                    Log.e("BitrixUpdate", "Ошибка на сервере при обновлении очков")
                    _sendResult.postValue(false)
                }
            }
        })
    }
}
