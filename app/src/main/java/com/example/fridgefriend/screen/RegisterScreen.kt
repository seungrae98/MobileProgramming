package com.example.fridgefriend.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.fridgefriend.navigation.Routes
import com.example.fridgefriend.viewmodel.UserDataViewModel
import com.example.fridgefriend.models.UserData
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavHostController, userDataViewModel: UserDataViewModel) {
    var userID by remember { mutableStateOf("") }
    var userPw by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var regiError by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    fun validateInput(): Boolean {
        return when {
            userID.isEmpty() || userPw.isEmpty() || userName.isEmpty() -> {
                errorMsg = "모든 내용을 입력해주세요"
                false
            }
            !Regex("^[a-zA-Z0-9]{3,10}\$").matches(userID) -> {
                errorMsg = "아이디는 3~10자 이내의 영문 대소문자, 숫자만 사용 가능합니다"
                false
            }
            !Regex("^[a-zA-Z0-9!@#\$%^&*()_+]{4,20}\$").matches(userPw) -> {
                errorMsg = "비밀번호는 4~20자 이내의 영문 대소문자, 숫자, 특수문자만 사용 가능합니다"
                false
            }
            !Regex("^[a-zA-Z]{3,20}\$").matches(userName) -> {
                errorMsg = "이름은 3~20자 이내의 영문만 사용 가능합니다"
                false
            }
            else -> {
                regiError = false
                true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (regiError) {
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

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = userID,
            onValueChange = { userID = it },
            label = { Text("아이디(3~10자 영문, 숫자)") }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = userPw,
            onValueChange = { userPw = it },
            label = { Text("비밀번호(4~20자 영문, 숫자, 특수문자)") },
            //visualTransformation = PasswordVisualTransformation()
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = userName,
            onValueChange = { userName = it },
            label = { Text("이름(3~20자 영문)") }
        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = {
                if (validateInput()) {
                    coroutineScope.launch {
                        val newUser = UserData(
                            id = userID,
                            pw = userPw,
                            name = userName,
                            favourite = mutableListOf(),
                            memo = mutableMapOf(),
                            contain = mutableMapOf()
                        )
                        userDataViewModel.addUser(newUser) { success ->
                            if (success) {
                                navController.navigate(Routes.Login.route) {
                                    popUpTo(Routes.Register.route) {
                                        inclusive = true
                                    }
                                }
                            } else {
                                regiError = true
                                errorMsg = "이미 존재하는 아이디입니다"
                            }
                        }
                    }
                } else {
                    regiError = true
                }
            },
            modifier = Modifier
                .clipToBounds()
                .background(MaterialTheme.colorScheme.primary, RectangleShape)
                .width(280.dp)
                .height(54.dp)
        ) {
            Text(text = "회원가입", color = Color.White)
        }

        Text(
            text = "로그인 화면으로 돌아가기",
            color = Color.Black,
            modifier = Modifier
                .clickable {
                    navController.navigate(Routes.Login.route) {
                        popUpTo(Routes.Register.route) {
                            inclusive = true
                        }
                    }
                }
                .padding(8.dp)
        )
    }
}
