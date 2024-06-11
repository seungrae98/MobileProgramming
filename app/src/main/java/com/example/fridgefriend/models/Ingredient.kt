package com.example.fridgefriend.models

import androidx.room.Entity

@Entity(tableName = "ingredient")
data class Ingredient(
    val id: Int = 0,
    val name: String = "",
    var contain: Boolean = false,
    var expireDate: String? = null
)