package com.example.fridgefriend.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    var searchText = mutableStateOf("")
    var searchQuery = mutableStateOf("")
    var isCardView = mutableStateOf(true)
    var selectedIngredients = mutableStateOf<List<String>>(emptyList())
    var selectedCard = mutableStateOf<CardData?>(null)
    var scrollState = mutableStateOf(0)
}
