package com.example.fridgefriend.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userdata")
data class UserData(
    var id: String = "",
    var pw: String = "",
    var name: String = "",
    var favourite: MutableList<Int> = mutableListOf(),
    var memo: MutableMap<Int, String> = mutableMapOf(),
    var contain: MutableMap<String, String> = mutableMapOf() // 유통기한, 키를 String으로 변경
)
