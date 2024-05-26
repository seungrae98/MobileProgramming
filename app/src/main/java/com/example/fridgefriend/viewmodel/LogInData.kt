package com.example.fridgefriend.viewmodel

import androidx.compose.runtime.MutableState

data class LogInData(
    var id:String,
    var password:String,
    var name:String,
    var favourite:MutableList<Int>,
    var contain:MutableMap<Int, String>
)