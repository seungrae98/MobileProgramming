package com.example.fridgefriend.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.fridgefriend.database.UserDataDBViewModel
import com.example.fridgefriend.navigation.Routes
import com.example.fridgefriend.database.UserDataDB
import com.example.fridgefriend.ui.theme.Main1
import com.example.fridgefriend.ui.theme.Main2
import com.example.fridgefriend.ui.theme.Main3
import com.example.fridgefriend.viewmodel.UserDataViewModel
import java.net.URLEncoder

@Composable
fun SettingsScreen(navController: NavHostController,
                   userDataDBViewModel: UserDataDBViewModel,
                   userDataViewModel: UserDataViewModel) {

    val userIndex = userDataViewModel.userIndex.intValue
    var isChangePwDialogVisible by remember { mutableStateOf(false) }
    var showToastMessage by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showToastMessage) {
        LaunchedEffect(showToastMessage) {
            Toast.makeText(context, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show()
            showToastMessage = false
        }
    }

    Column(modifier = Modifier.fillMaxSize()
        .background(Main1)
        .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start) {

        Text(text = "사용자 설정",
            modifier = Modifier.fillMaxWidth()
                .padding(start = 8.dp, bottom = 16.dp),
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold)

        Button(
            onClick = {
                isChangePwDialogVisible = true
            },
            colors = ButtonDefaults.buttonColors(containerColor = Main2),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "비밀번호 변경",
                    fontSize = 20.sp
                )
            }
        }

        Button(
            onClick = {
                userDataViewModel.loginStatus.value = false
                navController.navigate(Routes.Login.route) {
                    popUpTo(0)
                    launchSingleTop = true
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Main2),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "로그아웃",
                    fontSize = 20.sp
                )
            }
        }
    }

    if (isChangePwDialogVisible) {
        ChangePasswordDialog(
            onDismiss = { isChangePwDialogVisible = false },
            onChangePassword = { newPassword ->
                userDataViewModel.changePw(userDataDBViewModel, userIndex, newPassword)
                isChangePwDialogVisible = false
                showToastMessage = true
            }
        )
    }
}

@Composable
fun ChangePasswordDialog(onDismiss: () -> Unit, onChangePassword: (String) -> Unit) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordMatching by remember { mutableStateOf(true) }
    var regiError by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }

    fun validateInput(): Boolean {
        return when {
            !Regex("^[a-zA-Z0-9!@#\$%^&*()_+]{4,20}\$").matches(newPassword) -> {
                errorMsg = "비밀번호는 4~20자 이내의 영문 대소문자, 숫자, 특수문자만 사용 가능합니다"
                false
            }
            newPassword != confirmPassword -> {
                errorMsg = "서로 다른 비밀번호를 입력했습니다"
                false
            }
            else -> {
                regiError = false
                true
            }
        }
    }

    AlertDialog(
        containerColor = Main3,
        onDismissRequest = onDismiss,
        title = { Text(text = "Change Password") },
        text = {
            Column() {
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("New Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Rewrite Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                if (regiError) {
                    Text(
                        text = errorMsg,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (validateInput()) {
                        onChangePassword(newPassword)
                    } else {
                        regiError = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Main2)
            ) {
                Text("변경")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Main2)
            ) {
                Text("취소")
            }
        }
    )
}
