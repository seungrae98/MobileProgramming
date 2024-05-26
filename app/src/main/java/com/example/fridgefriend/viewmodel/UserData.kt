package com.example.fridgefriend.viewmodel

data class UserData(
    var id:String,
    var password:String,
    var name:String,
    var favourite:MutableList<Int>,
    var contain:MutableMap<Int, String>
)