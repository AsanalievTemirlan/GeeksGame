package com.example.geeksgame.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.geeksgame.ui.navigation.AppNavHost
import com.example.geeksgame.ui.theme.GeeksGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeeksGameTheme {
                AppNavHost()
            }
        }
    }
}