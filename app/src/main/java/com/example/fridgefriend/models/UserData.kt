package com.example.fridgefriend.models

data class UserData(
    var id:String,
    var pw:String,
    var name:String,
    var favourite:MutableList<Int>,
    var memo:MutableMap<Int, String>,
    var contain:MutableMap<Int, String> //유통기한
)
