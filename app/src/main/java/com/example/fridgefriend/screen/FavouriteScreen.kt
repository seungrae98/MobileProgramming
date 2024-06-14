package com.example.fridgefriend.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fridgefriend.database.UserDataDBViewModel
import com.example.fridgefriend.viewmodel.CardData
import com.example.fridgefriend.viewmodel.CardDataViewModel
import com.example.fridgefriend.viewmodel.UserDataViewModel

@Composable
fun FavouriteScreen(
    navController: NavHostController,
    userDataDBViewModel: UserDataDBViewModel,
    userDataViewModel: UserDataViewModel,
    cardDataViewModel: CardDataViewModel = viewModel()
) {
    val userIndex = userDataViewModel.userIndex.value
    var isCardView by rememberSaveable { mutableStateOf(true) }
    val listState = rememberLazyListState()
    val scrollState = rememberLazyListState()
    var selectedCard by rememberSaveable { mutableStateOf<CardData?>(null) }

    // 해당 유저의 좋아요 목록을 메뉴 목록(viewmodel)에 적용
    LaunchedEffect(userDataViewModel.userList[userIndex].favourite) {
        cardDataViewModel.cardList.forEach { card ->
            card.like = card.cardID in userDataViewModel.userList[userIndex].favourite
        }
    }

    // 해당 유저의 메모 목록을 메뉴 목록(viewmodel)에 적용
    LaunchedEffect(userDataViewModel.userList[userIndex].memo) {
        cardDataViewModel.cardList.forEachIndexed { index, card ->
            userDataViewModel.userList[userIndex].memo[card.cardID.toString()]?.let { memo ->
                cardDataViewModel.changeMemo(index, memo)
            }
        }
    }

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
                    .clickable { isCardView = !isCardView }
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .offset(
                            x = if (isCardView) 4.dp else 36.dp,
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
                Text(text = if (isCardView) "Card" else "List")
            }
        }

        val favouriteCardList = cardDataViewModel.cardList.filter { it.like }

        if (isCardView) {
            // 카드 형식 출력
            LazyRow(
                state = scrollState,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(favouriteCardList, key = { it.cardID }) { card ->
                    CardView(card, cardDataViewModel, userDataDBViewModel, userDataViewModel, onCardClick = { selectedCard = it })
                }
            }
        } else {
            // 리스트 형식 출력
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(favouriteCardList, key = { it.cardID }) { card ->
                    ListView(card, cardDataViewModel, userDataDBViewModel, userDataViewModel)
                }
            }
        }
    }

    selectedCard?.let { card ->
        CardDetailDialog(
            card = card,
            onDismissRequest = { selectedCard = null },
            cardDataViewModel = cardDataViewModel,
            userDataViewModel = userDataViewModel,
            userDataDBViewModel = userDataDBViewModel
        )
    }
}
