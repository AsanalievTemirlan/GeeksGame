package com.example.geeksgame.ui.screen


import android.widget.Toast
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.geeksgame.ui.navigation.Route
import com.example.geeksgame.ui.screen.viewModel.PlayerViewModel
import com.example.geeksgame.ui.theme.Black
import com.example.geeksgame.ui.theme.YellowExtra
import com.example.geeksgame.ui.theme.customFontFamily
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val userPrefs = remember { UserPrefs(context) }

    val prefix = "+996"
    var phoneNumber by remember { mutableStateOf(prefix) }

    val viewModel: PlayerViewModel = viewModel()
    val loginSuccess by viewModel.loginSuccess.collectAsState()

    // Реагируем на изменение loginSuccess
    LaunchedEffect(loginSuccess) {
        if (loginSuccess == true) {
            userPrefs.saveUserId(phoneNumber)
            navController.navigate(Route.MAIN)
        } else if (loginSuccess != null) {
            Toast.makeText(context, "Пользователь не найден. Зарегистрируйтесь.", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoImg(size = 150.dp)

        Spa(20.dp)

        Text(
            text = "ВХОД",
            fontSize = 22.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spa(40.dp)

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
                val cleaned = it.removePrefix(prefix).filter { c -> c.isDigit() }.take(9)
                phoneNumber = prefix + cleaned
            },
            label = { Text("НОМЕР ТЕЛЕФОНА", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            maxLines = 1,
            textStyle = TextStyle(color = Color.White),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.White
            )
        )

        Spa(30.dp)

        Button(
            onClick = {
                val isPhoneValid = phoneNumber.length == 13 && phoneNumber.startsWith(prefix)
                if (!isPhoneValid) {
                    Toast.makeText(context, "Введите корректный номер", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.loginPlayer(phoneNumber)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = YellowExtra),
            shape = RoundedCornerShape(6.dp)
        ) {
            Text(
                "ВОЙТИ",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = customFontFamily
            )
        }

        Spa(16.dp)

        TextButton(onClick = {
            navController.navigate(Route.REGISTRATION)
        }) {
            Text(
                "Ещё не зарегистрированы?",
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}
