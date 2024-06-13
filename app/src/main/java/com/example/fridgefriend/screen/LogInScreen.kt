package com.example.fridgefriend.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fridgefriend.navigation.Routes
import com.example.fridgefriend.viewmodel.UserDataViewModel
import kotlinx.coroutines.launch

@Composable
fun LogInScreen(
    navController: NavHostController,
    userDataViewModel: UserDataViewModel = viewModel()
) {
    var userId by remember { mutableStateOf("") }
    var userPw by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    fun validateInput(): Boolean {
        return when {
            userId.isEmpty() || userPw.isEmpty() -> {
                errorMsg = "아이디와 비밀번호를 입력해주세요"
                false
            }
            else -> true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (loginError) {
            Text(
                text = errorMsg,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        OutlinedTextField(
            value = userId,
            onValueChange = { userId = it },
            label = { Text("아이디") },
        )

        OutlinedTextField(
            value = userPw,
            onValueChange = { userPw = it },
            label = { Text("비밀번호") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Button(
            onClick = {
                if (validateInput()) {
                    loginError = false
                    coroutineScope.launch {
                        val isSuccess = userDataViewModel.checkInfo(userId, userPw)
                        if (isSuccess) {
                            userDataViewModel.loginStatus.value = true
                            userDataViewModel.setLoggedInUserId(userId)
                            navController.navigate(Routes.Home.route) {
                                popUpTo(Routes.Login.route) {
                                    inclusive = true
                                }
                            }
                        }
                        else {
                            loginError = true
                            errorMsg = "아이디 또는 비밀번호가 올바르지 않습니다"
                        }
                    }
                } else {
                    loginError = true
                }
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("로그인")
        }

        TextButton(
            onClick = {
                navController.navigate(Routes.Register.route) {
                    popUpTo(Routes.Login.route) {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("회원가입")
        }
    }
}
