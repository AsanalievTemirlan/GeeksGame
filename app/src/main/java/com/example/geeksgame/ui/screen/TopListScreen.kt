package com.example.geeksgame.ui.screen

import com.example.geeksgame.ui.screen.viewModel.PlayerViewModel
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.geeksgame.R
import com.example.geeksgame.ui.screen.viewModel.BitrixViewModel
import com.example.geeksgame.ui.theme.Black

@Composable
fun TopListScreen(navController: NavController) {
    val leaders = listOf("КОЛЯ", "ПЕТЯ","ПЕТЯ","ПЕТЯ","ПЕТЯ","ПЕТЯ","ПЕТЯ", "ЛЕША", "АНЯ", "ИВАН", "ИВАН", "АРТУР", "САША", "МИША", "ОЛЕГ")
    val bitrixViewModel: BitrixViewModel = viewModel()
    val viewModel: PlayerViewModel = viewModel()

    val list by viewModel.leaderboard.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Logo",
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "ТАБЛИЦА ЛИДЕРОВ",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .height(300.dp)
                .fillMaxWidth()
                .border(
                    BorderStroke(1.dp, Color.White),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                itemsIndexed(list) { index, player ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${index + 1}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.width(52.dp)
                            )
                            Text(
                                text = player.name, // предполагаем, что name — это поле класса Player
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }

                        Text(
                            text = "${player.score} очков", // предполагаем, что score — это поле
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }

        }
    }
}
