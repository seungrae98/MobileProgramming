package com.example.fridgefriend.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LogInDataViewModel : ViewModel(){

    var userList = mutableStateListOf<LogInData>()
        private set

    var userIndex = mutableStateOf(0)
    var loginStatus = mutableStateOf( false )

    init {
        // 예시로 하드코딩 했지만, DB에서 받아오는 방법도 있음
        val userDataSample1 = LogInData(
            "haandy98",
            "1234",
            "seungrae",
            mutableListOf<Int>(1, 2, 3),
            mutableMapOf<Int, String>(0 to "20240530", 1 to "20240531")
        )
        userList.add(userDataSample1)
    }

    fun checkInfo(id:String, passwd:String):Boolean{
        // 반복문 돌며 id pw 검사

        // 원래 로그인 한 유저의 정보가 들어가야 함
        userIndex.value = 0

        return true
    }
}