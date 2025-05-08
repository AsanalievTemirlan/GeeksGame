package com.example.geeksgame.ui.screen.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.geeksgame.model.Player
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class PlayerViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _loginSuccess = MutableStateFlow<Boolean?>(null)
    val loginSuccess: StateFlow<Boolean?> = _loginSuccess

    private val _leaderboard = MutableStateFlow<List<Player>>(emptyList())
    val leaderboard: StateFlow<List<Player>> = _leaderboard

    init {
        loadLeaderboard()
    }

    fun registerPlayer(name: String, phoneNum: String) {
        val playerData = hashMapOf(
            "name" to name,
            "phone_num" to phoneNum,
            "score" to 0
        )

        db.collection("players")
            .document(phoneNum)
            .set(playerData)
    }

    fun loginPlayer(phoneNum: String) {
        db.collection("players").document(phoneNum)
            .get()
            .addOnSuccessListener { document ->
                _loginSuccess.value = document.exists()
            }
            .addOnFailureListener {
                _loginSuccess.value = false
            }
    }


    fun updateScore(phoneNum: String, additionalPoints: Int) {
        val docRef = db.collection("players").document(phoneNum)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(docRef)
            val currentScore = snapshot.getLong("score") ?: 0L
            if (additionalPoints > currentScore) {
                transaction.update(docRef, "score", additionalPoints)
            }
        }.addOnSuccessListener {
            Log.e("ololo", "updateScore: all good", )
        }.addOnFailureListener { e ->
            e.printStackTrace()
        }
    }


    private fun loadLeaderboard(limit: Long = 10) {
        db.collection("players")
            .orderBy("score", Query.Direction.DESCENDING)
            .limit(limit)
            .get()
            .addOnSuccessListener { documents ->
                val players = documents.map { doc ->
                    Player(
                        id = doc.id,
                        name = doc.getString("name") ?: "",
                        phoneNum = doc.getString("phone_num") ?: "",
                        score = doc.getLong("score")?.toInt() ?: 0
                    )
                }
                _leaderboard.value = players
            }
    }
}
