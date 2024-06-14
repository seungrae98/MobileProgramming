package com.example.fridgefriend.viewmodel

// 모든 변경사항은 UserData에 정의
data class UserData(
    var id:String,
    var pw:String,
    var name:String,
    var favourite:MutableList<Int>,
    var memo:MutableMap<String, String>,
    var contain:MutableMap<String, String>
)