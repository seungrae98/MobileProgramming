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
import com.example.fridgefriend.viewmodel.IngredientDataViewModel
import com.example.fridgefriend.viewmodel.UserDataViewModel


@Composable
fun FridgeScreen(navController: NavHostController,
                 userDataViewModel: UserDataViewModel,
                 cardDataViewModel: CardDataViewModel = viewModel(),
                 ingredientDataViewModel: IngredientDataViewModel = viewModel()) {

    val scrollState = rememberScrollState()
    val userIndex = userDataViewModel.userIndex.value

    // 해당 유저의 재료 목록을 재료 목록(viewmodel)에 적용
    repeat(ingredientDataViewModel.ingredientList.size) {
        repeat(userDataViewModel.userList[userIndex].contain.size) { con ->
            if (ingredientDataViewModel.ingredientList[it].id == userDataViewModel.userList[userIndex].contain.keys.elementAt(con)) {
                ingredientDataViewModel.ingredientList[it].contain = true
                ingredientDataViewModel.changeExpireDate(it, userDataViewModel.userList[userIndex].contain.values.elementAt(con))
            }
        }
    }

    // 현재는 모든 재료 출력 수행
    // TODO: 출력 방법, 보유 재료 추가 기능, 유통기한 입력/수정 기능
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Text(
            text = userDataViewModel.userList[userIndex].name,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 20.sp
        )
        Text(
            text = userDataViewModel.userList[userIndex].contain.keys.joinToString(),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 20.sp
        )
        Text(
            text = "",
            fontSize = 20.sp
        )

        repeat(ingredientDataViewModel.ingredientList.size) {
            Text(
                text = ingredientDataViewModel.ingredientList[it].name,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp
            )
            Text(
                text = ingredientDataViewModel.ingredientList[it].contain.toString(),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp
            )
            Text(
                text = ingredientDataViewModel.ingredientList[it].expireDate,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp
            )
            Text(
                text = "",
                fontSize = 20.sp
            )

        }
    }
}