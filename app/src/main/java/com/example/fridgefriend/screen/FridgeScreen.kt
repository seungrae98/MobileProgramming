package com.example.fridgefriend.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fridgefriend.database.UserDataDB
import com.example.fridgefriend.database.UserDataDBViewModel
import com.example.fridgefriend.viewmodel.IngredientDataViewModel
import com.example.fridgefriend.viewmodel.UserDataViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Composable
fun FridgeScreen(
    navController: NavHostController,
    userDataViewModel: UserDataViewModel,
    userDataDBViewModel: UserDataDBViewModel,
    ingredientDataViewModel: IngredientDataViewModel = viewModel()
) {
    val userIndex = userDataViewModel.userIndex.value
    val scrollState = rememberScrollState()
    var expandedCategory by remember { mutableStateOf<String?>(null) }
    var showIngredientCheckDialog by remember { mutableStateOf(false) }

    // 현재 사용자의 재료 보유 정보를 업데이트
    LaunchedEffect(userDataViewModel.userList[userIndex].contain) {
        ingredientDataViewModel.ingredientList.forEachIndexed { index, ingredient ->
            userDataViewModel.userList[userIndex].contain[ingredient.id.toString()]?.let { expireDate ->
                ingredientDataViewModel.changeExpireDate(index, expireDate)
                ingredient.contain = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "내 냉장고",
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 38.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = { showIngredientCheckDialog = true },
                modifier = Modifier.size(48.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "재료 추가",
                    tint = Color.White,
                    modifier = Modifier.size(50.dp),
                )
            }
        }

        if (showIngredientCheckDialog) {
            IngredientCheckDialog(
                onDismissRequest = { showIngredientCheckDialog = false },
                ingredientDataViewModel = ingredientDataViewModel,
                userDataViewModel = userDataViewModel,
                userDataDBViewModel = userDataDBViewModel
            )
        }

        // 각 카테고리 버튼
        CategoryButton("육류", listOf(201, 202, 203, 204, 205, 206, 208, 209, 247, 248), expandedCategory, { expandedCategory = it }, ingredientDataViewModel, userDataViewModel)
        CategoryButton("해산물", listOf(207, 232, 233, 234, 235, 236, 237, 244, 246), expandedCategory, { expandedCategory = it }, ingredientDataViewModel, userDataViewModel)
        CategoryButton("야채", listOf(210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231), expandedCategory, { expandedCategory = it }, ingredientDataViewModel, userDataViewModel)
        CategoryButton("달걀/유제품", listOf(238, 239, 240, 241, 242, 243), expandedCategory, { expandedCategory = it }, ingredientDataViewModel, userDataViewModel)
        CategoryButton("기타", listOf(245, 249, 250, 251, 252), expandedCategory, { expandedCategory = it }, ingredientDataViewModel, userDataViewModel)
    }
}

// 카테고리 버튼
@Composable
fun CategoryButton(
    title: String,
    ids: List<Int>,
    expandedCategory: String?,
    onCategoryClick: (String?) -> Unit,
    ingredientDataViewModel: IngredientDataViewModel,
    userDataViewModel: UserDataViewModel
) {
    val isExpanded = expandedCategory == title
    val userIndex = userDataViewModel.userIndex.value
    val user = userDataViewModel.userList[userIndex]

    Column {
        TextButton(
            onClick = {
                onCategoryClick(if (isExpanded) null else title)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.White,
                    )
            }
        }

        Divider(
            color = Color.White,
            thickness = 3.dp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        if (isExpanded) {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                val categoryIngredients = user.contain.filter { (ingredientId, _) ->
                    ids.contains(ingredientId.toInt())
                }

                if (categoryIngredients.isEmpty()) {
                    Text(text = "보유 재료 없음", fontSize = 16.sp, color = Color.White, modifier = Modifier.padding(vertical = 8.dp),)
                } else {
                    categoryIngredients.forEach { (ingredientId, expireDate) ->
                        val ingredient = ingredientDataViewModel.ingredientList.find { it.id.toString() == ingredientId }
                        ingredient?.let {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = it.name, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                Text(text = "유통기한: $expireDate", color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

// 재료 체크 화면
@Composable
fun IngredientCheckDialog(
    onDismissRequest: () -> Unit,
    ingredientDataViewModel: IngredientDataViewModel,
    userDataViewModel: UserDataViewModel,
    userDataDBViewModel: UserDataDBViewModel
) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xfff2bc70)),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "재료 체크", color = Color.Black, fontSize = (30.sp), fontWeight = FontWeight.Bold)
                IconButton(onClick = onDismissRequest) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(50.dp),
                        )
                }
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .background(Color(0xfff2bc70))
            ) {
                item {
                    CategoryCheckSection("육류", listOf(201, 202, 203, 204, 205, 206, 208, 209, 247, 248), selectedCategory, { selectedCategory = it }, ingredientDataViewModel, userDataViewModel, userDataDBViewModel)
                    CategoryCheckSection("해산물", listOf(207, 232, 233, 234, 235, 236, 237, 244, 246), selectedCategory, { selectedCategory = it }, ingredientDataViewModel, userDataViewModel, userDataDBViewModel)
                    CategoryCheckSection("야채", listOf(210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231), selectedCategory, { selectedCategory = it }, ingredientDataViewModel, userDataViewModel, userDataDBViewModel)
                    CategoryCheckSection("달걀/유제품", listOf(238, 239, 240, 241, 242, 243), selectedCategory, { selectedCategory = it }, ingredientDataViewModel, userDataViewModel, userDataDBViewModel)
                    CategoryCheckSection("기타", listOf(245, 249, 250, 251, 252), selectedCategory, { selectedCategory = it }, ingredientDataViewModel, userDataViewModel, userDataDBViewModel)

                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text("확인", color = Color.Black, fontSize = (20.sp))
            }
        },
        containerColor = Color(0xfff2bc70)
    )
}

@Composable
fun CategoryCheckSection(
    title: String,
    ids: List<Int>,
    selectedCategory: String?,
    onCategoryClick: (String) -> Unit,
    ingredientDataViewModel: IngredientDataViewModel,
    userDataViewModel: UserDataViewModel,
    userDataDBViewModel: UserDataDBViewModel
) {
    val isExpanded = selectedCategory == title
    val checkedState = remember { mutableStateMapOf<Int, Boolean>() }
    var showExpireDateDialog by remember { mutableStateOf<Int?>(null) }
    val userIndex = userDataViewModel.userIndex.value
    val user = userDataViewModel.userList[userIndex]

    Column {
        TextButton(
            onClick = { onCategoryClick(if (isExpanded) "" else title) },
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, fontSize = 20.sp, color = Color.Black)
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
            Divider(
                color = Color.White,
                thickness = 3.dp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
        }

        if (isExpanded) {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                ids.forEach { id ->
                    val ingredient = ingredientDataViewModel.ingredientList.find { it.id == id }
                    ingredient?.let {
                        val isChecked = checkedState.getOrPut(it.id) { user.contain.containsKey(it.id.toString()) }

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = isChecked,
                                    onCheckedChange = { checked ->
                                        if (checked) {
                                            showExpireDateDialog = it.id
                                        } else {
                                            checkedState[it.id] = false
                                            user.contain.remove(it.id.toString())

                                            userDataDBViewModel.insertItem(UserDataDB(
                                                id = user.id,
                                                pw = user.pw,
                                                name = user.name,
                                                favourite = user.favourite,
                                                memo = user.memo,
                                                contain = user.contain.toMap()
                                            ))
                                        }
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Color(0xffd95a43),
                                        uncheckedColor = Color.Black
                                    )
                                )
                                Text(text = it.name, fontSize = 14.sp, color = Color.Black)
                            }
                        }

                        if (showExpireDateDialog == it.id) {
                            ExpireDateDialog(
                                onDismissRequest = { showExpireDateDialog = null },
                                onExpireDateConfirm = { expireDate ->
                                    user.contain[it.id.toString()] = expireDate

                                    userDataDBViewModel.insertItem(UserDataDB(
                                        id = user.id,
                                        pw = user.pw,
                                        name = user.name,
                                        favourite = user.favourite,
                                        memo = user.memo,
                                        contain = user.contain.toMap()
                                    ))
                                    checkedState[it.id] = true
                                    showExpireDateDialog = null
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

// 유통기한 입력
@Composable
fun ExpireDateDialog(
    onDismissRequest: () -> Unit,
    onExpireDateConfirm: (String) -> Unit
) {
    var expireDate by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val currentDate = LocalDate.now()

    fun validateDate(date: String): Boolean {
        return try {
            val parsedDate = LocalDate.parse(date, formatter)
            if (parsedDate >= currentDate) {
                errorMessage = ""
                true
            } else {
                errorMessage = "현재 날짜 이후여야 합니다."
                false
            }
        } catch (e: DateTimeParseException) {
            errorMessage = "유효한 날짜 형식이 아닙니다."
            false
        }
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "유통기한 입력") },
        text = {
            Column {
                TextField(
                    value = expireDate,
                    onValueChange = {
                        expireDate = it
                        isValid = it.length == 8 && it.all { char -> char.isDigit() } && validateDate(it)
                    },
                    label = { Text("유통기한 (8자리 숫자, yyyyMMdd)") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                if (!isValid) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (isValid && expireDate.isNotBlank()) {
                        onExpireDateConfirm(expireDate)
                    }
                    onDismissRequest()
                },
                enabled = isValid
            ) {
                Text("확인")
            }
        }
    )
}
