package com.example.fridgefriend.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavHostController
import com.example.fridgefriend.database.UserDataDBViewModel
import com.example.fridgefriend.viewmodel.UserDataViewModel
import com.example.fridgefriend.navigation.Routes

@Composable
fun LogInScreen(navController: NavHostController,
                userDataDBViewModel: UserDataDBViewModel,
                userDataViewModel: UserDataViewModel) {

    var userID by remember {
        mutableStateOf("")
    }

    var userPw by remember {
        mutableStateOf("")
    }

    var loginResult = userDataViewModel.checkInfo(userID, userPw)

    // TODO: 로그인 화면 구성
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        OutlinedTextField(value = userID,
            onValueChange = {userID =it},
            label = {Text("Enter ID")}
        )

        OutlinedTextField( value = userPw,
            onValueChange = { userPw = it },
            label = { Text("Enter password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Button(onClick = {

            if (loginResult) {
                userDataViewModel.loginStatus.value = true
                navController.navigate(Routes.Home.route) {
                    popUpTo(Routes.Login.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            } else {
                // TODO: 일치하는 정보가 없을 경우 처리
            }
        }) {
            Text(text = "로그인")
        }

        Button(onClick = {


            navController.navigate(Routes.Register.route)
        }) {
            Text(text = "회원가입")
        }
    }
}