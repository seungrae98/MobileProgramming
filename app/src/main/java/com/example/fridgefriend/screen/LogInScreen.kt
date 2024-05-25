package com.example.fridgefriend.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fridgefriend.viewmodel.LogInViewModel
import com.example.fridgefriend.navigation.Routes

@Composable
fun LogInScreen(navController: NavHostController) {

    val navViewModel: LogInViewModel = viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    var userID by remember {
        mutableStateOf("")
    }

    var userPasswd by remember {
        mutableStateOf("")
    }

    var loginresult = navViewModel.checkInfo(userID, userPasswd)

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Button(onClick = {
            navViewModel.setUserInfo(userID, userPasswd)

            // 로그인 시 조건 추가


            navViewModel.loginStatus.value = true
            if (navViewModel.loginStatus.value) {
                navController.navigate(Routes.Main.route) {
                    popUpTo(Routes.Login.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        }) {
            Text(text = "로그인")
        }

        Button(onClick = {
            navViewModel.setUserInfo(userID, userPasswd)
            navController.navigate(Routes.Register.route)
        }) {
            Text(text = "회원가입")
        }
    }
}