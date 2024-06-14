package com.example.fridgefriend.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class FavouriteViewModel : ViewModel() {
    var isCardView = mutableStateOf(true)
    var selectedCard = mutableStateOf<CardData?>(null)
    var scrollState = mutableStateOf(0)
}
