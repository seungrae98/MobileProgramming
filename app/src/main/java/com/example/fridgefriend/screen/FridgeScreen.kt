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
import com.example.fridgefriend.viewmodel.IngredientDataViewModel
import com.example.fridgefriend.viewmodel.UserDataViewModel


@Composable
fun FridgeScreen(navController: NavHostController,
                 ingredientDataViewModel: IngredientDataViewModel = viewModel(),
                 userDataViewModel: UserDataViewModel = viewModel()) {

    val scrollState = rememberScrollState()
    val userIndex = userDataViewModel.userIndex.value

    repeat(ingredientDataViewModel.ingredientList.size) {
        repeat(userDataViewModel.userList[userIndex].contain.size) { con ->
            if (ingredientDataViewModel.ingredientList[it].id == userDataViewModel.userList[userIndex].contain.keys.indexOf(con))
                ingredientDataViewModel.ingredientList[it].contain = true
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

        repeat(ingredientDataViewModel.ingredientList.size) {
            Text(
                text = ingredientDataViewModel.ingredientList[it].name,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 30.sp
            )
            Text(
                text = ingredientDataViewModel.ingredientList[it].contain.toString(),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 30.sp
            )
            Text(
                text = ingredientDataViewModel.ingredientList[it].expireDate,
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