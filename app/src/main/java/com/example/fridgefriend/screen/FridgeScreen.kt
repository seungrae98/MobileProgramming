package com.example.fridgefriend.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fridgefriend.database.UserDataDBViewModel
import com.example.fridgefriend.viewmodel.CardDataViewModel
import com.example.fridgefriend.viewmodel.IngredientDataViewModel
import com.example.fridgefriend.viewmodel.UserData
import com.example.fridgefriend.viewmodel.UserDataViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


@Composable
fun FridgeScreen(navController: NavHostController,
                 userDataViewModel: UserDataViewModel,
                 userDataDBViewModel: UserDataDBViewModel,
                 ingredientDataViewModel: IngredientDataViewModel = viewModel()) {

    val scrollState = rememberScrollState()
    var expandedCategory by remember { mutableStateOf<String?>(null) }
    var showIngredientCheckDialog by remember { mutableStateOf(false) }
    var user by remember { mutableStateOf<UserData?>(null) }

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
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "내 냉장고",
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 28.sp
            )
            IconButton(
                onClick = { showIngredientCheckDialog = true },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Ingredient",
                    modifier = Modifier.size(36.dp)
                )
            }
        }

        // 재료 체크 다이얼로그 표시
        if (showIngredientCheckDialog) {
            IngredientCheckDialog(
                onDismissRequest = { showIngredientCheckDialog = false },
                ingredientDataViewModel = ingredientDataViewModel,
                userDataViewModel = userDataViewModel
            )
        }

        // 각 카테고리별 버튼 및 분류
        CategoryButton("육류", listOf(201, 202, 203, 204, 205, 206, 208, 209, 247, 248), expandedCategory, { expandedCategory = it }, ingredientDataViewModel, user, userDataViewModel)
        CategoryButton("해산물", listOf(207, 232, 233, 234, 235, 236, 237, 244, 246), expandedCategory, { expandedCategory = it }, ingredientDataViewModel, user, userDataViewModel)
        CategoryButton("야채", listOf(210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231), expandedCategory, { expandedCategory = it }, ingredientDataViewModel, user, userDataViewModel)
        CategoryButton("기타", listOf(238, 239, 240, 241, 242, 243, 245, 249, 250, 251, 252), expandedCategory, { expandedCategory = it }, ingredientDataViewModel, user, userDataViewModel)
    }
}

@Composable
fun CategoryButton(
    title: String,
    ids: List<Int>,
    expandedCategory: String?,
    onCategoryClick: (String?) -> Unit,
    ingredientDataViewModel: IngredientDataViewModel,
    user: UserData?,
    userDataViewModel: UserDataViewModel
) {
    val isExpanded = expandedCategory == title

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
                Text(text = title, fontSize = 20.sp)
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }

        if (isExpanded) {
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            ) {
                user?.contain?.forEach { (ingredientId, expireDate) ->
                    val ingredient = ingredientDataViewModel.ingredientList.find { it.id.toString() == ingredientId }
                    ingredient?.let {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = it.name)
                            Text(text = expireDate, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IngredientCheckDialog(
    onDismissRequest: () -> Unit,
    ingredientDataViewModel: IngredientDataViewModel,
    userDataViewModel: UserDataViewModel
) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "재료 체크")
                IconButton(onClick = onDismissRequest) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxHeight(0.8f)
            ) {
                item {
                    CategoryCheckSection("육류", listOf(1, 2, 3, 4, 5, 6, 8, 9, 47, 48), selectedCategory, { selectedCategory = it }, ingredientDataViewModel, userDataViewModel)
                    CategoryCheckSection("해산물", listOf(7, 32, 33, 34, 35, 36, 37, 44, 46), selectedCategory, { selectedCategory = it }, ingredientDataViewModel, userDataViewModel)
                    CategoryCheckSection("야채", listOf(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31), selectedCategory, { selectedCategory = it }, ingredientDataViewModel, userDataViewModel)
                    CategoryCheckSection("기타", listOf(38, 39, 40, 41, 42, 43, 45, 49, 50, 51, 52), selectedCategory, { selectedCategory = it }, ingredientDataViewModel, userDataViewModel)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text("확인")
            }
        }
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
    val dialogState = remember { mutableStateMapOf<Int, Boolean>() }

    Column {
        TextButton(
            onClick = {
                onCategoryClick(if (isExpanded) "" else title)
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
                Text(text = title, fontSize = 20.sp)
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }

        if (isExpanded) {
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            ) {
                ids.forEach { id ->
                    val ingredient = ingredientDataViewModel.ingredientList.getOrNull(id - 1)
                    ingredient?.let {
                        val isChecked = checkedState.getOrPut(it.id - 1) { it.contain }
                        val showExpireDateDialog = dialogState.getOrPut(it.id - 1) { false }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = isChecked,
                                    onCheckedChange = { checked ->
                                        if (checked) {
                                            dialogState[it.id - 1] = true
                                        } else {
                                            userDataViewModel.(it.id)
                                            checkedState[it.id - 1] = false
                                        }
                                    }
                                )
                                Text(text = it.name, fontSize = 14.sp)
                            }
                        }

                        if (showExpireDateDialog) {
                            ExpireDateDialog(
                                onDismissRequest = { dialogState[it.id - 1] = false },
                                onExpireDateConfirm = { expireDate ->
                                    userDataViewModel.addExpireDateToContain(it.id, expireDate)
                                    userDataDBViewModel.updateItem()
                                    checkedState[it.id - 1] = true
                                    dialogState[it.id - 1] = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

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
        title = {
            Text(text = "유통기한 입력")
        },
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