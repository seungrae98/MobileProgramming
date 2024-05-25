package com.example.fridgefriend.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LogInViewModel : ViewModel(){

    var userList = mutableStateListOf<LogInData>()
        private set

    init {
        // 예시로 몇가지 하드코딩 했지만, DB에서 받아오는 방법도 있음
        val userDataSample1 = LogInData(
            "haandy98",
            "1234",
            "seungrae",
            listOf<Int>(1, 2, 3)
        )
        userList.add(userDataSample1)
    }

    var userID:String? = null
    var userPasswd:String? = null

    var loginStatus = mutableStateOf( false )

    fun checkInfo(id:String, passwd:String):LogInData{
        // 반복문 돌며 id pw 검사

        return userList[0]
    }

    fun setUserInfo(id:String, passwd:String){
        userID = id
        userPasswd = passwd
    }
}