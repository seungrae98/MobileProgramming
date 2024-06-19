package com.example.fridgefriend.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fridgefriend.database.UserDataDBViewModel
import com.example.fridgefriend.viewmodel.CardDataViewModel
import com.example.fridgefriend.viewmodel.FavouriteViewModel
import com.example.fridgefriend.viewmodel.UserDataViewModel

@Composable
fun FavouriteScreen(
    navController: NavHostController,
    userDataDBViewModel: UserDataDBViewModel,
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
            cardDataViewModel.updateCardMemo(cardID.toInt(), memo)
        }
    }

    val favouriteCardList by remember { derivedStateOf { cardDataViewModel.cardList.filter { it.like } } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF68056)) // 색2
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
                // 리스트/카드뷰 토글 버튼
                Box(
                    modifier = Modifier
                        .border(3.dp, Color(0xFFD95A43), RoundedCornerShape(16.dp))
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
                                .background(if (isListView) Color(0xFFD95A43) else Color.White)
                                .clickable { isListView = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "List",
                                color = if (isListView) Color.White else Color(0xFFD95A43),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(if (!isListView) Color(0xFFD95A43) else Color.White)
                                .clickable { isListView = false },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Card",
                                color = if (!isListView) Color.White else Color(0xFFD95A43),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (isListView) {
                // 리스트 형식 출력
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(favouriteCardList, key = { it.cardID }) { card ->
                        ListView(card, cardDataViewModel, userDataDBViewModel, userDataViewModel, onCardClick = { selectedCard = it })
                    }
                }
            } else {
                // 카드 형식 출력
                LazyRow(
                    state = scrollState,
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(favouriteCardList, key = { it.cardID }) { card ->
                        CardView(card, cardDataViewModel, userDataDBViewModel, userDataViewModel, onCardClick = { selectedCard = it })
                    }
                }
            }
        }
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
