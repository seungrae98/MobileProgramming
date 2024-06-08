package com.example.fridgefriend.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class UserDataViewModel : ViewModel(){

    var userList = mutableStateListOf<UserData>()
        private set

    var userIndex = mutableIntStateOf(0)
    var loginStatus = mutableStateOf( false)

    init {
        // 예시로 하드코딩 했지만, DB에서 받아오는 방법도 있음
        val userDataSample1 = UserData(
            "id1",
            "1234",
            "user1",
            mutableListOf<Int>(1, 2, 3),
            mutableMapOf<Int, String>(1 to "memo for card1 from user1", 5 to "memo for card5 from user1"),
            mutableMapOf<Int, String>(1 to "20240530", 2 to "20240531")
        )
        userList.add(userDataSample1)

        val userDataSample2 = UserData(
            "id2",
            "1234",
            "user2",
            mutableListOf<Int>(2, 5),
            mutableMapOf<Int, String>(2 to "memo for card2 from user2", 3 to "memo for card3 from user2", 4 to "memo for card4 from user2"),
            mutableMapOf<Int, String>(3 to "20240601", 4 to "20240602")
        )
        userList.add(userDataSample2)
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

    fun isUserIdExists(id: String): Boolean {
        // 유저 id 중복 체크
        return userList.any { it.id == id }
    }

    //UserListAdd 함수 만들기, 함수 호출해서 회원가입하면 이거로 새 뷰모델에 데이터 추가되게
    fun addUser(id: String, pw: String, name: String) {
        val newUser = UserData(
            id = id,
            pw = pw,
            name = name,
            favourite = mutableListOf(),
            memo = mutableMapOf(),
            contain = mutableMapOf()
        )
        userList.add(newUser)
    }

    // 유저의 contain에 새로운 재료의 유통기한 추가
    fun addExpireDateToContain(userIndex: Int, ingredientId: Int, expireDate: String) {
        if (userIndex >= 0 && userIndex < userList.size) {
            userList[userIndex].contain[ingredientId] = expireDate
        }
    }

    // 유저의 contain에서 기존 재료의 유통기한 제거
    fun removeExpireDateFromContain(userIndex: Int, ingredientId: Int) {
        if (userIndex >= 0 && userIndex < userList.size) {
            userList[userIndex].contain.remove(ingredientId)
        }
    }
}