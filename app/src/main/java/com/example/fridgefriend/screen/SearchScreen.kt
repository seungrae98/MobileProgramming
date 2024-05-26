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
fun SearchScreen(navController: NavHostController,
                 cardDataViewModel: CardDataViewModel = viewModel(),
                 logInDataViewModel: UserDataViewModel = viewModel()) {

    val scrollState = rememberScrollState()
    val userIndex = logInDataViewModel.userIndex.value

    // 해당 유저의 좋아요 목록을 메뉴 목록(viewmodel)에 적용
    repeat(cardDataViewModel.cardList.size) {
        repeat(logInDataViewModel.userList[userIndex].favourite.size) {fav ->
            if (cardDataViewModel.cardList[it].cardID == logInDataViewModel.userList[userIndex].favourite[fav])
                cardDataViewModel.cardList[it].like = true
        }
    }

    // 현재는 모든 메뉴 출력 수행
    // TODO: 검색 기능 (메뉴 이름 검색 / 보유 재료 검색), 출력 방법(카드 형식, 리스트 형식), 좋아요만 보기 기능 등
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Text(
            text = logInDataViewModel.userList[userIndex].name,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 30.sp
        )
        Text(
            text = logInDataViewModel.userList[userIndex].favourite.joinToString(),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 30.sp
        )
        Text(
            text = "",
            fontSize = 30.sp
        )

        repeat(cardDataViewModel.cardList.size) {
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