package com.example.fridgefriend.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.fridgefriend.viewmodel.UserDataViewModel
import com.example.fridgefriend.navigation.Routes

@Composable
fun LogInScreen(navController: NavHostController,
                userDataViewModel: UserDataViewModel) {

    var userID by remember {
        mutableStateOf("")
    }

    var userPw by remember {
        mutableStateOf("")
    }

    var loginError by remember {
        mutableStateOf(false)
    }

    var loginResult = userDataViewModel.checkInfo(userID, userPw)

    // TODO: 로그인 화면 구성
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        if (loginError) {
            Text(
                text = "아이디 혹은 비밀번호를 정확히 입력해주세요.",
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFCDD2))
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        OutlinedTextField(value = userID,
            onValueChange = {userID =it},
            label = {Text("아이디")}
        )

        OutlinedTextField( value = userPw,
            onValueChange = { userPw = it },
            label = { Text("비밀번호") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(25.dp))

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
                loginError = true
            }
        },
            modifier = Modifier
                .clipToBounds()
                .background(MaterialTheme.colorScheme.primary, RectangleShape)
                .width(280.dp)
                .height(54.dp)
        ) {
            Text(text = " 로그인 ")
        }

        Spacer(modifier = Modifier.height(5.dp))

        Button(onClick = {
            navController.navigate(Routes.Register.route)
        },
            modifier = Modifier
                .clipToBounds()
                .background(MaterialTheme.colorScheme.primary, RectangleShape)
                .width(280.dp)
                .height(54.dp)
        ) {
            Text(text = "회원가입")
        }
    }
}