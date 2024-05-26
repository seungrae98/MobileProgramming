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
import com.example.fridgefriend.viewmodel.UserDataViewModel

@Composable
fun FavouriteScreen(navController: NavHostController,
                    cardDataViewModel: CardDataViewModel = viewModel(),
                    userDataViewModel: UserDataViewModel = viewModel()) {

    val scrollState = rememberScrollState()
    val userIndex = userDataViewModel.userIndex.value

    // 해당 유저의 좋아요 목록을 메뉴 목록(viewmodel)에 적용
    repeat(cardDataViewModel.cardList.size) {
        repeat(userDataViewModel.userList[userIndex].favourite.size) { fav ->
            if (cardDataViewModel.cardList[it].cardID == userDataViewModel.userList[userIndex].favourite[fav])
                cardDataViewModel.cardList[it].like = true
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Text(
            text = userDataViewModel.userList[userIndex].name,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 30.sp
        )
        Text(
            text = userDataViewModel.userList[userIndex].favourite.joinToString(),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 30.sp
        )
        Text(
            text = "",
            fontSize = 30.sp
        )

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