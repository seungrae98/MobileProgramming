package com.example.fridgefriend.viewmodel

data class CardData(
    var cardID:Int,
    var name:String,
    var mainIngredient:List<String>,
    var memo:String = "",
    var recipeLink:List<String>,
    var like:Boolean = false
)