package com.example.fridgefriend.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fridgefriend.database.UserDataDB
import com.example.fridgefriend.database.UserDataDBViewModel

class UserDataViewModel : ViewModel(){

    var userList = mutableStateListOf<UserData>()
        private set

    var loggedInUserId: String? = null
        private set

    var userIndex = mutableIntStateOf(0)
    var loginStatus = mutableStateOf( false )

    init {
        // 예시로 하드코딩 했지만, DB에서 받아오는 방법도 있음
        val userDataSample1 = UserData(
            "id1",
            "1234",
            "user1",
            mutableListOf<Int>(101, 102, 103),
            mutableMapOf<String, String>("101" to "memo for card1 from user1", "105" to "memo for card5 from user1"),
            mutableMapOf<String, String>("201" to "20240530", "202" to "20240531")
        )
        userList.add(userDataSample1)
    }

    fun getUserData(userList: List<UserDataDB>) {
        userList.forEach {
            val userSample = UserData(
                id = it.id,
                pw = it.pw,
                name = it.name,
                favourite = it.favourite.toMutableList(),
                memo = it.memo.toMutableMap(),
                contain = it.contain.toMutableMap()
            )
            this.userList.add(userSample)
        }
    }

    fun checkInfo(id:String, pw:String): Boolean{
        // 유저 id pw 비교 후 같은 id pw가 있을 경우 해당 유저의 인텍스 저장 후 true 반환
        repeat(userList.size) {
            if (userList[it].id == id && userList[it].pw == pw) {
                userIndex.value = it
                return true
            }
        }
        return false
    }

    fun checkID(id: String): Boolean {
        // 유저 id 비교 후 같은 id가 있을 경우 true 반환
        repeat(userList.size) {
            if (userList[it].id == id) {
                return true
            }
        }
        return false
    }

    fun addUserData(userDataDBViewModel: UserDataDBViewModel, userData: UserData) {
        val userSample = UserDataDB(
            id = userData.id,
            pw = userData.pw,
            name = userData.name,
            favourite = userData.favourite.toList(),
            memo = userData.memo.toMap(),
            contain = userData.contain.toMap(),
        )
        userDataDBViewModel.insertItem(userSample)
        userList.add(userData)
    }

    fun changePw(userDataDBViewModel: UserDataDBViewModel, index: Int, newPw: String) {
        userList[index] = userList[index].copy(pw = newPw)
        val userSample = UserDataDB(
            id = userList[index].id,
            pw = userList[index].pw,
            name = userList[index].name,
            favourite = userList[index].favourite.toList(),
            memo = userList[index].memo.toMap(),
            contain = userList[index].contain.toMap(),
        )
        userDataDBViewModel.updateItem(userSample)
    }

    fun setLoggedInUserId(userId: String) {
        loggedInUserId = userId
    }
}