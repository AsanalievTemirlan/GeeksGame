package com.example.geeksgame.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.geeksgame.ui.navigation.Route
import com.example.geeksgame.ui.theme.Black
import com.example.geeksgame.ui.theme.YellowExtra
import com.example.geeksgame.ui.theme.customFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(navController: NavController) {

    var phoneNumber by remember { mutableStateOf("") }

    val prefix = "+996"
    val maxLength = 13 // +996XXXXXXXX (9 цифр после кода)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 30.dp)
        ) {
            LogoImg()
            Spa(30.dp)
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "ЗАРЕГИСТРИРУЙТЕСЬ\nЧТОБЫ НАЧАТЬ",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Spa(60.dp)
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("ИМЯ", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.White),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                )
            )
            Spa(20.dp)
            OutlinedTextField(
                value = if (phoneNumber.startsWith(prefix)) phoneNumber else prefix,
                onValueChange = {
                    // Оставляем только цифры после +996 и ограничиваем их длину
                    val newValue = it.removePrefix(prefix).filter { char -> char.isDigit() }.take(9)
                    phoneNumber = prefix + newValue
                },
                label = { Text("НОМЕР ТЕЛЕФОНА", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.White),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.White,
                )
            )
            Spa(30.dp)
            Button(
                onClick = {
                /* регистрация */
                    navController.navigate(Route.MAIN)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = YellowExtra // Твой яркий фиолетовый
                ),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text("ЗАРЕГИСТРИРОВАТЬСЯ", color = Color.White, fontSize = 16.sp, fontFamily = customFontFamily)
            }
        }
    }
}
