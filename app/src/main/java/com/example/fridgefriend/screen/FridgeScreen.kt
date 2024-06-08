@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fridgefriend.viewmodel.CardDataViewModel
import com.example.fridgefriend.viewmodel.IngredientDataViewModel
import com.example.fridgefriend.viewmodel.UserDataViewModel


@Composable
fun FridgeScreen(navController: NavHostController,
                 userDataViewModel: UserDataViewModel,
                 cardDataViewModel: CardDataViewModel = viewModel(),
                 ingredientDataViewModel: IngredientDataViewModel = viewModel()) {

    val scrollState = rememberScrollState()
    val userIndex = userDataViewModel.userIndex.value
    var expandedCategory by remember { mutableStateOf<String?>(null) } //확정된 카테고리
    var showIngredientCheckDialog by remember { mutableStateOf(false) }  // 재료 체크 다이얼로그 상태

    // 해당 유저의 재료 목록을 재료 목록(viewmodel)에 적용
    repeat(ingredientDataViewModel.ingredientList.size) {
        repeat(userDataViewModel.userList[userIndex].contain.size) { con ->
            if (ingredientDataViewModel.ingredientList[it].id == userDataViewModel.userList[userIndex].contain.keys.elementAt(con)
            ) {
                ingredientDataViewModel.ingredientList[it].contain = true
                ingredientDataViewModel.changeExpireDate(
                    it, userDataViewModel.userList[userIndex].contain.values.elementAt(con)
                )
            }
        }
    }

    // 현재는 모든 재료 출력 수행
    // TODO: 출력 방법, 보유 재료 추가 기능, 유통기한 입력/수정 기능
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        // 상단 바
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "내 냉장고",
                style = MaterialTheme.typography.bodyLarge,
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

            // 재료 체크 다이얼로그 표시
            if (showIngredientCheckDialog) {
                IngredientCheckDialog(
                    onDismissRequest = { showIngredientCheckDialog = false },
                    ingredientDataViewModel = ingredientDataViewModel,
                    userDataViewModel = userDataViewModel,
                    userIndex = userIndex
                )
            }
        }

        //카테고리별 버튼 및 분류
        CategoryButton("육류", listOf(1, 2, 3, 4, 5, 6, 8, 9, 47, 48), expandedCategory, { expandedCategory = it }, ingredientDataViewModel, userDataViewModel, userIndex)
        CategoryButton("해산물", listOf(7, 32, 33, 34, 35, 36, 37, 44, 46), expandedCategory, { expandedCategory = it }, ingredientDataViewModel, userDataViewModel, userIndex)
        CategoryButton("야채", listOf(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31), expandedCategory, { expandedCategory = it }, ingredientDataViewModel, userDataViewModel, userIndex)
        CategoryButton("기타", listOf(38, 39, 40, 41, 42, 43, 45, 49, 50, 51, 52), expandedCategory, { expandedCategory = it }, ingredientDataViewModel, userDataViewModel, userIndex)
    }
}

@Composable
fun CategoryButton(
    title: String,
    ids: List<Int>,
    expandedCategory: String?,
    onCategoryClick: (String?) -> Unit,
    ingredientDataViewModel: IngredientDataViewModel,
    userDataViewModel: UserDataViewModel,
    userIndex: Int
) {
    val isExpanded = expandedCategory == title

    Column {
        TextButton(
            onClick = {
                onCategoryClick(if (isExpanded) null else title) //확장/축소 토글
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
                    val ingredient = ingredientDataViewModel.ingredientList.find { it.id == id && it.contain }
                    ingredient?.let {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = it.name)
                            Text(text = userDataViewModel.userList[userIndex].contain[it.id] ?: "유통기한 정보 없음")
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
    userDataViewModel: UserDataViewModel,
    userIndex: Int
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
                    CategoryCheckSection("육류", listOf(1, 2, 3, 4, 5, 6, 8, 9, 47, 48), selectedCategory, { selectedCategory = it }, ingredientDataViewModel, userDataViewModel, userIndex)
                    CategoryCheckSection("해산물", listOf(7, 32, 33, 34, 35, 36, 37, 44, 46), selectedCategory, { selectedCategory = it }, ingredientDataViewModel, userDataViewModel, userIndex)
                    CategoryCheckSection("야채", listOf(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31), selectedCategory, { selectedCategory = it }, ingredientDataViewModel, userDataViewModel, userIndex)
                    CategoryCheckSection("기타", listOf(38, 39, 40, 41, 42, 43, 45, 49, 50, 51, 52), selectedCategory, { selectedCategory = it }, ingredientDataViewModel, userDataViewModel, userIndex)
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
//재료 체크창
fun CategoryCheckSection(
    title: String,
    ids: List<Int>,
    selectedCategory: String?,
    onCategoryClick: (String) -> Unit,
    ingredientDataViewModel: IngredientDataViewModel,
    userDataViewModel: UserDataViewModel,
    userIndex: Int
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
                        val isChecked = checkedState.getOrPut(it.id-1) { it.contain }
                        val showExpireDateDialog = dialogState.getOrPut(it.id-1) { false }

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
                                            dialogState[it.id-1] = true
                                        } else {
                                            ingredientDataViewModel.changeContain(it.id-1)
                                            ingredientDataViewModel.changeExpireDate(it.id-1, "")
                                            checkedState[it.id-1] = false
                                        }
                                    }
                                )
                                Text(text = it.name, fontSize = 14.sp)
                            }
                        }

                        if (showExpireDateDialog) {
                            ExpireDateDialog(
                                onDismissRequest = { dialogState[it.id-1] = false },
                                onExpireDateConfirm = { expireDate ->
                                    ingredientDataViewModel.changeContain(it.id-1)
                                    ingredientDataViewModel.changeExpireDate(it.id-1, expireDate)
                                    checkedState[it.id-1] = true
                                    dialogState[it.id-1] = false
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

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = "유통기한 입력")
        },
        text = {
            TextField(
                value = expireDate,
                onValueChange = { expireDate = it },
                label = { Text("유통기한") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        },
        confirmButton = {
            TextButton(onClick = {
                if (expireDate.isNotBlank()) {
                    onExpireDateConfirm(expireDate)
                }
                onDismissRequest()
            }) {
                Text("확인")
            }
        }
    )
}





    /*{
        Text(
            text = userDataViewModel.userList[userIndex].name,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 20.sp
        )
        Text(
            text = userDataViewModel.userList[userIndex].contain.keys.joinToString(),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 20.sp
        )
        Text(
            text = "",
            fontSize = 20.sp
        )

        repeat(ingredientDataViewModel.ingredientList.size) {
            Text(
                text = ingredientDataViewModel.ingredientList[it].name,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp
            )
            Text(
                text = ingredientDataViewModel.ingredientList[it].contain.toString(),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp
            )
            Text(
                text = ingredientDataViewModel.ingredientList[it].expireDate,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp
            )
            Text(
                text = "",
                fontSize = 20.sp
            )

        }*/