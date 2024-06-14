package com.example.fridgefriend.database

data class UserDataDB(
    var id:String,
    var pw:String,
    var name:String,
    var favourite:List<Int>,
    var memo:Map<String, String>,
    var contain:Map<String, String>
) {
    // No-argument constructor
    constructor() : this("", "", "", listOf(), mapOf(), mapOf())
}