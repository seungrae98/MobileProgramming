package com.example.fridgefriend.models

data class Ingredient(
    val id: Int = 0,
    val name: String = "",
    var contain: Boolean = false,
    var expireDate: String? = null
)