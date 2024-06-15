package com.example.fridgefriend.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fridgefriend.database.UserDataDB
import com.example.fridgefriend.database.UserDataDBViewModel
import com.example.fridgefriend.navigation.Routes
import com.example.fridgefriend.viewmodel.*
import java.net.URLEncoder
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    userDataDBViewModel: UserDataDBViewModel,
    userDataViewModel: UserDataViewModel,
    cardDataViewModel: CardDataViewModel = viewModel(),
    ingredientDataViewModel: IngredientDataViewModel = viewModel(),
    searchViewModel: SearchViewModel = viewModel()
) {
    val userIndex by remember { userDataViewModel.userIndex }
    var isListView by remember { searchViewModel.isCardView }
    var searchText by remember { searchViewModel.searchText }
    var searchQuery by remember { searchViewModel.searchQuery }
    var showIngredientDialog by rememberSaveable { mutableStateOf(false) }
    var selectedIngredients by remember { searchViewModel.selectedIngredients }
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = searchViewModel.scrollState.value)
    val scrollState = rememberLazyListState(initialFirstVisibleItemIndex = searchViewModel.scrollState.value)
    var selectedCard by remember { searchViewModel.selectedCard }

    // Save scroll state when leaving the screen
    DisposableEffect(Unit) {
        onDispose {
            searchViewModel.scrollState.value = if (isListView) listState.firstVisibleItemIndex else scrollState.firstVisibleItemIndex
        }
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    // 좋아요 및 메모 정보 반영
    LaunchedEffect(Unit) {
        userDataViewModel.userList[userIndex].favourite.forEach { favouriteId ->
            cardDataViewModel.updateCardLike(favouriteId, true)
        }
        userDataViewModel.userList[userIndex].memo.forEach { (cardID, memo) ->
            cardDataViewModel.updateCardMemo(cardID.toInt(), memo)
        }
    }

    // 해당 유저의 재료 목록을 재료 목록(viewmodel)에 적용
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
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                modifier = Modifier
                    .padding(8.dp)
            )
            Button(
                onClick = {
                    searchQuery = searchText
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ) {
                Text(text = "검색")
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .width(72.dp)
                    .height(40.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                    .clip(CircleShape)
                    .clickable { isListView = !isListView }
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .offset(
                            x = if (isListView) 36.dp else 4.dp,
                            y = 4.dp
                        )
                        .background(Color.White, shape = CircleShape)
                )
            }
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .padding(start = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = if (isListView) "List" else "Card")
            }

            Spacer(modifier = Modifier.width(20.dp))

            Button(
                onClick = {
                    showIngredientDialog = true
                    keyboardController?.hide() // 키보드를 숨깁니다.
                    focusManager.clearFocus()
                },
                modifier = Modifier
            ) {
                Text(text = "보유 재료 검색")
            }
        }

        // 필터링된 메뉴 출력
        val filteredCardList by remember(searchQuery, selectedIngredients) {
            derivedStateOf {
                cardDataViewModel.cardList.filter {
                    it.name.contains(searchQuery, ignoreCase = true) &&
                            (selectedIngredients.isEmpty() || it.mainIngredient.any { ingredient -> selectedIngredients.contains(ingredient) })
                }
            }
        }

        if (selectedIngredients.isEmpty()) {
            if (isListView) {
                // 리스트 형식 출력
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(filteredCardList, key = { it.cardID }) { card ->
                        ListView(card, cardDataViewModel, userDataDBViewModel, userDataViewModel, onCardClick = { selectedCard = it })
                    }
                }
            } else {
                // 카드 형식 출력
                LazyRow(
                    state = scrollState,
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(filteredCardList, key = { it.cardID }) { card ->
                        CardView(card, cardDataViewModel, userDataDBViewModel, userDataViewModel, onCardClick = { selectedCard = it })
                    }
                }
            }
        } else {
            if (isListView) {
                // 보유 재료 검색 후 리스트 형식 출력
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(filteredCardList, key = { it.cardID }) { card ->
                        ListView(card, cardDataViewModel, userDataDBViewModel, userDataViewModel, onCardClick = { selectedCard = it })
                    }
                }
            } else {
                // 보유 재료 검색 후 카드 형식 출력
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    selectedIngredients.forEach { ingredient ->
                        item {
                            Text(
                                text = ingredient,
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.Black,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                        item {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                items(filteredCardList.filter { it.mainIngredient.contains(ingredient) }, key = { it.cardID }) { card ->
                                    CardView(card, cardDataViewModel, userDataDBViewModel, userDataViewModel, onCardClick = { selectedCard = it })
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showIngredientDialog) {
        IngredientDialog(
            ingredientDataViewModel = ingredientDataViewModel,
            userDataViewModel = userDataViewModel,
            onDismissRequest = {
                showIngredientDialog = false
                focusManager.clearFocus() // 포커스를 해제하여 키보드가 뜨지 않도록 합니다.
            },
            onApply = { selected ->
                selectedIngredients = selected
                showIngredientDialog = false
            }
        )
    }

    selectedCard?.let { card ->
        CardDetailDialog(
            card = card,
            onDismissRequest = { selectedCard = null },
            navController = navController,
            cardDataViewModel = cardDataViewModel,
            userDataViewModel = userDataViewModel,
            userDataDBViewModel = userDataDBViewModel
        )
    }
}

@Composable
fun CardView(
    card: CardData,
    cardDataViewModel: CardDataViewModel,
    userDataDBViewModel: UserDataDBViewModel,
    userDataViewModel: UserDataViewModel,
    onCardClick: (CardData) -> Unit
) {
    val userIndex by remember { userDataViewModel.userIndex }
    var like by remember { mutableStateOf(card.like) }

    LaunchedEffect(card.like) {
        like = card.like
    }

    Column(
        modifier = Modifier
            .width(300.dp)
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
            .clickable { onCardClick(card) }
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = card.imageResId),
            contentDescription = card.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(
                text = card.name,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp
            )
            IconButton(onClick = {
                like = !like
                cardDataViewModel.updateCardLike(card.cardID, like)
                if (like) {
                    userDataViewModel.userList[userIndex].favourite.add(card.cardID)
                } else {
                    userDataViewModel.userList[userIndex].favourite.remove(card.cardID)
                }
                val userDBSample = UserDataDB(
                    id = userDataViewModel.userList[userIndex].id,
                    pw = userDataViewModel.userList[userIndex].pw,
                    name = userDataViewModel.userList[userIndex].name,
                    favourite = userDataViewModel.userList[userIndex].favourite.toList(),
                    memo = userDataViewModel.userList[userIndex].memo.toMap(),
                    contain = userDataViewModel.userList[userIndex].contain.toMap()
                )
                userDataDBViewModel.updateItem(userDBSample)
            }) {
                Icon(
                    imageVector = if (like) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = null,
                    tint = if (like) Color.Red else Color.Gray
                )
            }
        }
        Text(
            text = card.mainIngredient.joinToString(", "),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun ListView(
    card: CardData,
    cardDataViewModel: CardDataViewModel,
    userDataDBViewModel: UserDataDBViewModel,
    userDataViewModel: UserDataViewModel,
    onCardClick: (CardData) -> Unit
) {
    val userIndex by remember { userDataViewModel.userIndex }
    var like by remember { mutableStateOf(card.like) }

    LaunchedEffect(card.like) {
        like = card.like
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(16.dp)
            .clickable { onCardClick(card) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = card.name,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 20.sp
        )
        IconButton(onClick = {
            like = !like
            cardDataViewModel.updateCardLike(card.cardID, like)
            if (like) {
                userDataViewModel.userList[userIndex].favourite.add(card.cardID)
            } else {
                userDataViewModel.userList[userIndex].favourite.remove(card.cardID)
            }
            val userDBSample = UserDataDB(
                id = userDataViewModel.userList[userIndex].id,
                pw = userDataViewModel.userList[userIndex].pw,
                name = userDataViewModel.userList[userIndex].name,
                favourite = userDataViewModel.userList[userIndex].favourite.toList(),
                memo = userDataViewModel.userList[userIndex].memo.toMap(),
                contain = userDataViewModel.userList[userIndex].contain.toMap()
            )
            userDataDBViewModel.updateItem(userDBSample)
        }) {
            Icon(
                imageVector = if (like) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = null,
                tint = if (like) Color.Red else Color.Gray
            )
        }
    }
}

@Composable
fun IngredientDialog(
    ingredientDataViewModel: IngredientDataViewModel,
    userDataViewModel: UserDataViewModel,
    onDismissRequest: () -> Unit,
    onApply: (List<String>) -> Unit
) {
    val scrollState = rememberScrollState()
    val userIndex by remember { userDataViewModel.userIndex }
    val userContainIngredients = userDataViewModel.userList[userIndex].contain.keys.map { ingredientId ->
        ingredientId.toInt()
    }
    val ingredientList = ingredientDataViewModel.ingredientList
    var selectedIngredients by rememberSaveable { mutableStateOf(listOf<String>()) }
    val categories = listOf("1", "2", "3")
    val ingredientsByCategory = listOf(
        ingredientList.subList(0, 20),
        ingredientList.subList(20, 40),
        ingredientList.subList(40, 52)
    )
    val expandedState = remember { mutableStateMapOf("1" to true, "2" to true, "3" to true) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "재료 선택") },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(8.dp)
            ) {
                categories.forEachIndexed { index, category ->
                    val isExpanded = expandedState[category] ?: true
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    expandedState[category] = !isExpanded
                                }
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "카테고리 $category",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.Black
                            )
                            Icon(
                                imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                                contentDescription = null
                            )
                        }
                        if (isExpanded) {
                            ingredientsByCategory[index].chunked(2).forEach { pair ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    pair.forEach { ingredient ->
                                        val isContained = userContainIngredients.contains(ingredient.id)
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Checkbox(
                                                checked = selectedIngredients.contains(ingredient.name),
                                                onCheckedChange = {
                                                    if (it) {
                                                        selectedIngredients = selectedIngredients + ingredient.name
                                                    } else {
                                                        selectedIngredients = selectedIngredients - ingredient.name
                                                    }
                                                }
                                            )
                                            Text(
                                                text = ingredient.name,
                                                color = if (isContained) Color.Red else Color.Unspecified
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = { onApply(selectedIngredients) }) {
                Text(text = "적용")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text(text = "취소")
            }
        }
    )
}

@Composable
fun CardDetailDialog(
    card: CardData,
    onDismissRequest: () -> Unit,
    navController: NavHostController,
    cardDataViewModel: CardDataViewModel,
    userDataViewModel: UserDataViewModel,
    userDataDBViewModel: UserDataDBViewModel
) {
    val userIndex by remember { userDataViewModel.userIndex }
    var memo by rememberSaveable { mutableStateOf(card.memo) }
    var like by remember { mutableStateOf(card.like) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = card.name) },
        text = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = painterResource(id = card.imageResId),
                    contentDescription = card.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = card.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 20.sp
                    )
                    IconButton(onClick = {
                        like = !like
                    }) {
                        Icon(
                            imageVector = if (like) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = null,
                            tint = if (like) Color.Red else Color.Gray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Ingredients: ${card.mainIngredient.joinToString(", ")}")
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val url = card.recipeLink[0]
                        onDismissRequest()
                        navController.navigate(Routes.WebView.route + "/${URLEncoder.encode(url, "UTF-8")}")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 8.dp) // Adjust padding as needed
                ) {
                    Text(text = "레시피 바로가기")
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = memo,
                    onValueChange = { memo = it },
                    label = { Text("Memo") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                cardDataViewModel.updateCardMemo(card.cardID, memo)
                cardDataViewModel.updateCardLike(card.cardID, like)
                if (like) {
                    userDataViewModel.userList[userIndex].favourite.add(card.cardID)
                } else {
                    userDataViewModel.userList[userIndex].favourite.remove(card.cardID)
                }
                userDataViewModel.userList[userIndex].memo[card.cardID.toString()] = memo
                val userDBSample = UserDataDB(
                    id = userDataViewModel.userList[userIndex].id,
                    pw = userDataViewModel.userList[userIndex].pw,
                    name = userDataViewModel.userList[userIndex].name,
                    favourite = userDataViewModel.userList[userIndex].favourite.toList(),
                    memo = userDataViewModel.userList[userIndex].memo.toMap(),
                    contain = userDataViewModel.userList[userIndex].contain.toMap()
                )
                userDataDBViewModel.updateItem(userDBSample)
                onDismissRequest()
            }) {
                Text(text = "저장")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text(text = "취소")
            }
        }
    )
}
