package com.example.fridgefriend.screen

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fridgefriend.MainActivity
import com.example.fridgefriend.R
import com.example.fridgefriend.database.UserDataDBViewModel
import com.example.fridgefriend.makeNotification
import com.example.fridgefriend.viewmodel.UserDataViewModel
import com.example.fridgefriend.navigation.Routes
import com.example.fridgefriend.ui.theme.Main1
import com.example.fridgefriend.ui.theme.Main2
import com.example.fridgefriend.viewmodel.IngredientData
import com.example.fridgefriend.viewmodel.IngredientDataViewModel
import kotlinx.coroutines.CoroutineScope
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(navController: NavHostController,
                userDataDBViewModel: UserDataDBViewModel,
                userDataViewModel: UserDataViewModel,
                ingredientDataViewModel: IngredientDataViewModel = viewModel()) {

    val context = LocalContext.current
    var userId by remember { mutableStateOf("") }
    var userPw by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }

    val window = (LocalContext.current as Activity).window
    window.statusBarColor = Main2.toArgb()
    window.navigationBarColor = Main2.toArgb()

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
            .background(Main1), // 전체 배경색 설정
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
                        .border(4.dp, Main2, RoundedCornerShape(8.dp))
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
                        .border(4.dp, Main2, RoundedCornerShape(8.dp))
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

                        val newIntent = Intent(context, MainActivity::class.java)
                        newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_SINGLE_TOP or
                                Intent.FLAG_ACTIVITY_CLEAR_TOP
                        val pendingIntent = PendingIntent.getActivity(
                            context,
                            100,
                            newIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )

                        val userIndex = userDataViewModel.userIndex.value
                        var ingredientExpireList = mutableStateListOf<IngredientData>()
                        var ingredientExpireDay = mutableStateListOf<Int>()
                        val date = Date(System.currentTimeMillis())

                        val calendar = Calendar.getInstance()
                        calendar.time = date
                        calendar.add(Calendar.DATE, 7)
                        val dateAfter7Days = calendar.time

                        val dateFormat = "yyyyMMdd"
                        val simpleDateFormat = SimpleDateFormat(dateFormat)
                        val simpleDate: String = simpleDateFormat.format(date)
                        val simpleDateAfter7Days: String = simpleDateFormat.format(dateAfter7Days)

                        ingredientDataViewModel.ingredientList.forEachIndexed { index, ingredient ->
                            userDataViewModel.userList[userIndex].contain[ingredient.id.toString()]?.let { expireDate ->
                                ingredient.expireDate = expireDate
                                ingredient.contain = true
                                if (ingredient.expireDate.toInt() <= simpleDateAfter7Days.toInt()) {
                                    ingredientExpireList.add(ingredient)
                                    ingredientExpireDay.add(ingredient.expireDate.toInt() - simpleDate.toInt())
                                }
                            }
                        }

                        repeat(ingredientExpireList.size) {
                            if (ingredientExpireDay[it] >= 0)
                                makeNotification(context, ingredientExpireList[it].name + " 유통기한이 " + ingredientExpireDay[it] +"일 남았습니다!", pendingIntent, ingredientExpireList[it].id)
                            else
                                makeNotification(context, ingredientExpireList[it].name + " 유통기한이 지났습니다.", pendingIntent, ingredientExpireList[it].id)
                        }

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
            colors = ButtonDefaults.buttonColors(containerColor = Main2),
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
            colors = ButtonDefaults.buttonColors(containerColor = Main2),
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
