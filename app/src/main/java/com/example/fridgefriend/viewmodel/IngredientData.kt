package com.example.fridgefriend.viewmodel

import androidx.compose.runtime.MutableState

data class IngredientData(
    var id:Int,
    var name:String,
    var contain:Boolean = false,
    var expireDate:String
)