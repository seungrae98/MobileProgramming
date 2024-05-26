package com.example.fridgefriend.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.fridgefriend.navigation.Routes
import com.example.fridgefriend.viewmodel.UserDataViewModel

@Composable
fun SettingsScreen(navController: NavHostController,
                   userDataViewModel: UserDataViewModel) {


    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {



        Button(onClick = {


        }) {
            Text(text = "비밀번호 변경")
        }

        Button(onClick = {
            userDataViewModel.loginStatus.value = false
            navController.navigate(Routes.Login.route) {
                popUpTo(0)
                launchSingleTop = true
            }
        }) {
            Text(text = "로그아웃")
        }
    }
}