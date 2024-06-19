package com.example.fridgefriend.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.fridgefriend.R
import com.example.fridgefriend.database.UserDataDBViewModel
import com.example.fridgefriend.viewmodel.UserDataViewModel
import com.example.fridgefriend.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(navController: NavHostController,
                userDataDBViewModel: UserDataDBViewModel,
                userDataViewModel: UserDataViewModel) {

    var userId by remember { mutableStateOf("") }
    var userPw by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }

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
            .padding(16.dp)
            .background(Color(0xFFF68056)), // 전체 배경색 설정
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .height(300.dp)
                .width(400.dp)
                .padding(bottom = 16.dp),

        )

        if (loginError) {
            Text(
                text = errorMsg,
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFCDD2))
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        BasicTextField(
            value = userId,
            onValueChange = { userId = it },
            decorationBox = { innerTextField ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .border(4.dp, Color(0xFFD95A43), RoundedCornerShape(8.dp))
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    if (userId.isEmpty()) {
                        Text("아이디", fontSize = 18.sp)
                    }
                    else{
                        Text("아이디", fontSize = 12.sp, color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = userPw,
            onValueChange = { userPw = it },
            visualTransformation = PasswordVisualTransformation(),
            decorationBox = { innerTextField ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .border(4.dp, Color(0xFFD95A43), RoundedCornerShape(8.dp))
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    if (userPw.isEmpty()) {
                        Text("비밀번호", fontSize = 18.sp)
                    }
                    else{
                        Text("비밀번호", fontSize = 12.sp, color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = {
                if (validateInput()) {
                    loginError = false
                    val isSuccess = userDataViewModel.checkInfo(userId, userPw)
                    if (isSuccess) {
                        userDataViewModel.loginStatus.value = true
                        userDataViewModel.setLoggedInUserId(userId)
                        navController.navigate(Routes.Home.route) {
                            popUpTo(Routes.Login.route) {
                                inclusive = true
                            }
                        }
                    } else {
                        loginError = true
                        errorMsg = "아이디 또는 비밀번호가 올바르지 않습니다"
                    }
                } else {
                    loginError = true
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD95A43)),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .width(280.dp)
                .height(54.dp)
        ) {
            Text("로그인", color = Color.White, fontSize = 22.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                navController.navigate(Routes.Register.route) {
                    popUpTo(Routes.Login.route) {
                        inclusive = true
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD95A43)),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .width(280.dp)
                .height(54.dp)
        ) {
            Text("회원가입", color = Color.White, fontSize = 22.sp)
        }
    }
}
