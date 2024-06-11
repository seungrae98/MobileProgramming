package com.example.fridgefriend.models

import androidx.room.Entity

@Entity(tableName = "carddata")
data class CardData(
    var cardID: Int = 0,
    var name: String = "",
    var mainIngredient: List<String> = listOf(),
    var memo: String = "",
    var recipeLink: List<String> = listOf(),
    var like: Boolean = false
)
