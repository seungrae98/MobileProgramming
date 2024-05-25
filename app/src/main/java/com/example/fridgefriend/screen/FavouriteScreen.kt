package com.example.fridgefriend.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fridgefriend.viewmodel.CardDataViewModel

@Composable
fun FavouriteScreen(navController: NavHostController, cardDataViewModel: CardDataViewModel = viewModel()) {

    // 간단하게 하드코딩된 메뉴 중 좋아요 true 인 것만 출력하도록 했음.

    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        repeat(cardDataViewModel.cardList.size) {
            if (cardDataViewModel.cardList[it].like) {
                Text(
                    text = cardDataViewModel.cardList[it].name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 30.sp
                )
                Text(
                    text = cardDataViewModel.cardList[it].mainIngredient.joinToString(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 30.sp
                )
                Text(
                    text = cardDataViewModel.cardList[it].recipeLink.joinToString(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 30.sp
                )
                Text(
                    text = "",
                    fontSize = 30.sp
                )
            }
        }
    }
}