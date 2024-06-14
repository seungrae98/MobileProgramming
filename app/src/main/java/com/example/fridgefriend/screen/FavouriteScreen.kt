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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fridgefriend.viewmodel.CardData
import com.example.fridgefriend.viewmodel.CardDataViewModel
import com.example.fridgefriend.viewmodel.FavouriteViewModel
import com.example.fridgefriend.viewmodel.IngredientDataViewModel
import com.example.fridgefriend.viewmodel.UserDataViewModel

@Composable
fun FavouriteScreen(
    navController: NavHostController,
    userDataViewModel: UserDataViewModel,
    cardDataViewModel: CardDataViewModel = viewModel(),
    favouriteViewModel: FavouriteViewModel = viewModel()
) {
    val userIndex by remember { userDataViewModel.userIndex }
    var isListView by remember { favouriteViewModel.isCardView }
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = favouriteViewModel.scrollState.value)
    val scrollState = rememberLazyListState(initialFirstVisibleItemIndex = favouriteViewModel.scrollState.value)
    var selectedCard by remember { favouriteViewModel.selectedCard }

    // Save scroll state when leaving the screen
    DisposableEffect(Unit) {
        onDispose {
            favouriteViewModel.scrollState.value = if (isListView) listState.firstVisibleItemIndex else scrollState.firstVisibleItemIndex
        }
    }

    // 좋아요 및 메모 정보 반영
    LaunchedEffect(userDataViewModel.userList[userIndex].favourite) {
        userDataViewModel.userList[userIndex].favourite.forEach { favouriteId ->
            cardDataViewModel.updateCardLike(favouriteId, true)
        }
        userDataViewModel.userList[userIndex].memo.forEach { (cardID, memo) ->
            cardDataViewModel.updateCardMemo(cardID, memo)
        }
    }

    val favouriteCardList by remember { derivedStateOf { cardDataViewModel.cardList.filter { it.like } } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
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
        }

        if (isListView) {
            // 리스트 형식 출력
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(favouriteCardList, key = { it.cardID }) { card ->
                    FavouriteListView(card, cardDataViewModel, userDataViewModel, onCardClick = { selectedCard = it })
                }
            }
        } else {
            // 카드 형식 출력
            LazyRow(
                state = scrollState,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(favouriteCardList, key = { it.cardID }) { card ->
                    FavouriteCardView(card, cardDataViewModel, userDataViewModel, onCardClick = { selectedCard = it })
                }
            }
        }
    }

    selectedCard?.let { card ->
        CardDetailDialog(
            card = card,
            onDismissRequest = { selectedCard = null },
            cardDataViewModel = cardDataViewModel,
            userDataViewModel = userDataViewModel
        )
    }
}

@Composable
fun FavouriteListView(
    card: CardData,
    cardDataViewModel: CardDataViewModel,
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
fun FavouriteCardView(
    card: CardData,
    cardDataViewModel: CardDataViewModel,
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
