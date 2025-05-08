package com.example.geeksgame.ui.screen

import com.example.geeksgame.ui.screen.viewModel.PlayerViewModel
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.geeksgame.ui.navigation.Route
import com.example.geeksgame.ui.theme.Black
import com.example.geeksgame.ui.theme.YellowExtra
import com.example.geeksgame.ui.theme.customFontFamily
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import com.example.geeksgame.ui.screen.viewModel.BitrixViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(navController: NavController) {

    // --- Focus Management ---
    val nameFocusRequester = remember { FocusRequester() }
    val phoneFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var phoneNumber by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    val prefix = "+996"
    val maxLength = 13
    val context = LocalContext.current
    val userPrefs = UserPrefs(context)


    val bitrixViewModel: BitrixViewModel = viewModel()
    val viewModel: PlayerViewModel = viewModel()

    val sendResult by bitrixViewModel.sendResult.observeAsState()

    LaunchedEffect(sendResult) {
        when (sendResult) {
            true -> navController.navigate(Route.MAIN)
            false -> Toast.makeText(context, "Ошибка при регистрации. Попробуйте снова.", Toast.LENGTH_SHORT).show()
            null -> {}
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Black),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 30.dp)
        ) {
            LogoImg(size = 150.dp)
            Spa(30.dp)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "ЗАРЕГИСТРИРУЙТЕСЬ\nЧТОБЫ НАЧАТЬ",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            Spa(60.dp)

            // --- Name Field ---
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("ИМЯ", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(nameFocusRequester),
                textStyle = TextStyle(color = Color.White),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        phoneFocusRequester.requestFocus()
                    }
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                ),
                maxLines = 1
            )

            Spa(20.dp)

            // --- Phone Field ---
            OutlinedTextField(
                value = if (phoneNumber.startsWith(prefix)) phoneNumber else prefix,
                onValueChange = {
                    val newValue = it.removePrefix(prefix).filter { char -> char.isDigit() }.take(9)
                    phoneNumber = prefix + newValue
                },
                label = { Text("НОМЕР ТЕЛЕФОНА", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(phoneFocusRequester),
                maxLines = 1,
                textStyle = TextStyle(color = Color.White),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        val isPhoneValid = phoneNumber.length == 13 && phoneNumber.startsWith(prefix)
                        if (name.isBlank() || !isPhoneValid) {
                            Toast.makeText(
                                context,
                                "Пожалуйста, заполните все поля корректно",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            focusManager.clearFocus()
                            bitrixViewModel.sendLeadToBitrix(name, phoneNumber)
                            viewModel.registerPlayer(name, phoneNumber)
                            userPrefs.saveUserId(phoneNumber)
                            userPrefs.setRegistered(true)
                        }
                    }
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.White,
                )
            )

            Spa(30.dp)

            // --- Submit Button ---
            Button(
                onClick = {
                    val isPhoneValid = phoneNumber.length == 13 && phoneNumber.startsWith(prefix)
                    if (name.isBlank() || !isPhoneValid) {
                        Toast.makeText(
                            context,
                            "Пожалуйста, заполните все поля корректно",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        bitrixViewModel.sendLeadToBitrix(name, phoneNumber)
                        viewModel.registerPlayer(name, phoneNumber)
                        userPrefs.saveUserId(phoneNumber)
                        userPrefs.setRegistered(true)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = YellowExtra),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    "ЗАРЕГИСТРИРОВАТЬСЯ",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontFamily = customFontFamily
                )
            }
        }
    }
}