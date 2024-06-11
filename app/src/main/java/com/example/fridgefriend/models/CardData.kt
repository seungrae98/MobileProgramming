package com.example.fridgefriend.models

data class CardData(
    var cardID: Int = 0,
    var name: String = "",
    var mainIngredient: List<String> = listOf(),
    var memo: String = "",
    var recipeLink: List<String> = listOf(),
    var like: Boolean = false
)
