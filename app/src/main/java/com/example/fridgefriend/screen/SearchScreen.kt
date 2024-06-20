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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fridgefriend.database.UserDataDB
import com.example.fridgefriend.database.UserDataDBViewModel
import com.example.fridgefriend.ui.theme.Main1
import com.example.fridgefriend.ui.theme.Main2
import com.example.fridgefriend.ui.theme.Main3
import com.example.fridgefriend.viewmodel.*
import java.net.URLEncoder
import kotlinx.coroutines.launch

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
    val listState = rememberLazyListState()
    val scrollState = rememberLazyListState()
    var selectedCard by remember { searchViewModel.selectedCard }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

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

    // 스크롤 상태를 맨 위로 설정하는 함수
    suspend fun resetListState() {
        listState.scrollToItem(0)
        scrollState.scrollToItem(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Main1)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            coroutineScope.launch {
                                searchQuery = searchText
                                keyboardController?.hide()
                                focusManager.clearFocus()
                                resetListState()
                            }
                        }
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .border(3.dp, Main2, RoundedCornerShape(28.dp)),
                    shape = RoundedCornerShape(28.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    searchQuery = searchText
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                    resetListState()
                                }
                            },
                            modifier = Modifier
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search",
                                tint = Main2, // 색1
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // 리스트/카드뷰 토글 버튼
                Box(
                    modifier = Modifier
                        .border(3.dp, Main2, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .width(140.dp)
                        .height(40.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(if (isListView) Main2 else Color.White)
                                .clickable { isListView = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "List",
                                color = if (isListView) Color.White else Main2,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(if (!isListView) Main2 else Color.White)
                                .clickable { isListView = false },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Card",
                                color = if (!isListView) Color.White else Main2,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // 보유 재료 검색 버튼
                Button(
                    onClick = {
                        coroutineScope.launch {
                            showIngredientDialog = true
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            resetListState()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Main2)
                ) {
                    Text(text = "보유 재료 검색", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

                // Reset button
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            searchQuery = ""
                            searchText = ""
                            isListView = true
                            selectedIngredients = emptyList()
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            resetListState()
                        }
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .border(3.dp, Main2, shape = RoundedCornerShape(20.dp))
                        .background(Color.White, shape = RoundedCornerShape(20.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset",
                        tint = Main2,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 필터링된 메뉴 출력
            val filteredCardList by remember(searchQuery, selectedIngredients) {
                derivedStateOf {
                    cardDataViewModel.cardList.filter {
                        it.name.contains(searchQuery, ignoreCase = true) &&
                                (selectedIngredients.isEmpty() || it.mainIngredient.any { ingredient -> selectedIngredients.contains(ingredient) })
                    }
                }
            }

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
                if (selectedIngredients.isEmpty() && searchQuery.isEmpty()) {
                    // 검색어가 없고, 보유 재료도 선택되지 않은 경우 모든 메뉴를 출력
                    LazyRow(
                        state = scrollState,
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(cardDataViewModel.cardList, key = { it.cardID }) { card ->
                            CardView(card, cardDataViewModel, userDataDBViewModel, userDataViewModel, onCardClick = { selectedCard = it })
                        }
                    }
                } else if (selectedIngredients.isEmpty()) {
                    // 검색어가 있는 경우 필터링된 메뉴를 출력
                    LazyRow(
                        state = scrollState,
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(filteredCardList, key = { it.cardID }) { card ->
                            CardView(card, cardDataViewModel, userDataDBViewModel, userDataViewModel, onCardClick = { selectedCard = it })
                        }
                    }
                } else {
                    // 보유 재료가 선택된 경우 재료별로 분류된 메뉴를 출력
                    LazyColumn(
                        state = scrollState,
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
    }

    if (showIngredientDialog) {
        IngredientDialog(
            ingredientDataViewModel = ingredientDataViewModel,
            userDataViewModel = userDataViewModel,
            onDismissRequest = {
                showIngredientDialog = false
                focusManager.clearFocus()
            },
            onApply = { selected ->
                coroutineScope.launch {
                    selectedIngredients = selected
                    showIngredientDialog = false
                    resetListState()
                }
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
            .border(3.dp, Main2, RoundedCornerShape(8.dp))
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
                fontSize = 24.sp
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
                    tint = if (like) Color.Red else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Text(
            text = card.mainIngredient.joinToString(", "),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 20.sp,
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
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(3.dp, Main2, RoundedCornerShape(8.dp))
            .padding(16.dp)
            .clickable { onCardClick(card) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier.weight(1f).padding(end = 8.dp)
        ) {
            Text(
                text = card.name,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp,
                maxLines = 2,
                modifier = Modifier.padding(end = 40.dp)
            )
        }
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
    val userIndex by remember { userDataViewModel.userIndex }
    val userContainIngredients = userDataViewModel.userList[userIndex].contain.keys.map { ingredientId ->
        ingredientId.toInt()
    }
    val ingredientList = ingredientDataViewModel.ingredientList
    var selectedIngredients by rememberSaveable { mutableStateOf(listOf<String>()) }

    // 카테고리와 재료 정보
    val categories = mapOf(
        "육류" to listOf("소고기", "차돌박이", "우삼겹", "돼지고기 앞다리살", "삼겹살", "대패삼겹살", "닭고기", "닭가슴살", "스팸", "베이컨"),
        "해물" to listOf("쭈꾸미", "새우", "명란젓", "오징어", "고등어", "연어", "미역", "참치캔", "김"),
        "채소" to listOf("부추", "콩나물", "대파", "양파", "감자", "청경채", "쪽파", "오이", "브로콜리", "파슬리", "애호박", "청양고추", "무", "당근", "마늘", "토마토", "숙주", "피망", "팽이버섯", "고추", "파프리카", "양배추"),
        "달걀/유제품" to listOf("달걀", "메추리알", "우유", "파마산치즈", "체다치즈", "모짜렐라치즈"),
        "기타" to listOf("두부", "떡볶이 떡", "옥수수통조림", "식빵", "어묵")
    )
    val expandedState = remember { mutableStateMapOf<String, Boolean>().apply {
        categories.keys.forEach { this[it] = true }
    } }

    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Main3, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = "재료 선택",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.typography.bodyLarge.fontSize.value.dp))
                    Text(
                        text = "* 보유 중인 재료",
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp),
                        textAlign = TextAlign.End
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Column {
                        categories.forEach { (category, ingredients) ->
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
                                        text = category,
                                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                        color = Color.Black
                                    )
                                    Icon(
                                        imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                                        contentDescription = null
                                    )
                                }
                                if (isExpanded) {
                                    ingredients.chunked(2).forEach { pair ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            pair.forEach { ingredient ->
                                                val isContained = userContainIngredients.contains(ingredientList.find { it.name == ingredient }?.id ?: -1)
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                    modifier = Modifier.weight(1f)
                                                ) {
                                                    Checkbox(
                                                        checked = selectedIngredients.contains(ingredient),
                                                        onCheckedChange = {
                                                            if (it) {
                                                                selectedIngredients = selectedIngredients + ingredient
                                                            } else {
                                                                selectedIngredients = selectedIngredients - ingredient
                                                            }
                                                        }
                                                    )
                                                    Text(
                                                        text = ingredient,
                                                        fontWeight = FontWeight.Bold,
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
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Button(
                        onClick = onDismissRequest,
                        colors = ButtonDefaults.buttonColors(containerColor = Main2)
                    ) {
                        Text("취소", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onApply(selectedIngredients) },
                        colors = ButtonDefaults.buttonColors(containerColor = Main2)
                    ) {
                        Text("적용", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
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

    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Main3, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight() // 높이를 내용에 맞게 조정
            ) {
                Text(
                    text = card.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Center
                )
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
                        text = "재료: ${card.mainIngredient.joinToString(", ")}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 20.sp,
                        modifier = Modifier.weight(1f)
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
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val url = card.recipeLink[0]
                        onDismissRequest()
                        navController.navigate("webview/${URLEncoder.encode(url, "UTF-8")}")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Main2),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Text(text = "레시피 바로가기", color = Color.White, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = memo,
                    onValueChange = { memo = it },
                    label = { Text("Memo") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onDismissRequest,
                        colors = ButtonDefaults.buttonColors(containerColor = Main2)
                    ) {
                        Text("취소", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
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
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Main2)
                    ) {
                        Text("저장", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
