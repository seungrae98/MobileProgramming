package com.example.fridgefriend.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.fridgefriend.models.UserData
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserDataViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance().getReference("users")
    val userList = mutableStateListOf<UserData>()
    var userIndex = mutableStateOf(0)
    var loginStatus = mutableStateOf(false)  // 로그인 상태
    var checkInfoMessage = mutableStateOf("")

    init {
        // Realtime Database에서 데이터를 불러옴
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(UserData::class.java)
                    user?.let { userList.add(it) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
    }

    fun addExpireDateToContain(userIndex: Int, ingredientId: Int, expireDate: String) {
        val user = userList[userIndex]
        user.contain[ingredientId.toString()] = expireDate
        saveUserDataToDatabase(userIndex)
    }

    fun removeExpireDateFromContain(userIndex: Int, ingredientId: Int) {
        val user = userList[userIndex]
        user.contain.remove(ingredientId.toString())
        saveUserDataToDatabase(userIndex)
    }

    private fun saveUserDataToDatabase(userIndex: Int) {
        val user = userList[userIndex]
        db.child(user.id).setValue(user)
    }

    // 로그인 기능
    fun login(id: String, pw: String): Boolean {
        val user = userList.find { it.id == id && it.pw == pw }
        return if (user != null) {
            userIndex.value = userList.indexOf(user)
            loginStatus.value = true
            checkInfoMessage.value = "로그인 성공"
            true
        } else {
            checkInfoMessage.value = "로그인 실패: 아이디 또는 비밀번호가 일치하지 않습니다."
            false
        }
    }

    // 로그아웃 기능
    fun logout() {
        loginStatus.value = false
        checkInfoMessage.value = "로그아웃 되었습니다."
    }

    // 회원가입 기능
    fun signUp(newUser: UserData): Boolean {
        val exists = userList.any { it.id == newUser.id }
        return if (!exists) {
            userList.add(newUser)
            db.child(newUser.id).setValue(newUser)
            checkInfoMessage.value = "회원가입 성공"
            true
        } else {
            checkInfoMessage.value = "회원가입 실패: 이미 존재하는 아이디입니다."
            false
        }
    }

    // checkInfo 함수: 입력받은 id와 pw를 가진 유저가 있는지 확인
    fun checkInfo(id: String, pw: String): Boolean {
        val user = userList.find { it.id == id && it.pw == pw }
        return if (user != null) {
            userIndex.value = userList.indexOf(user)
            true
        } else {
            false
        }
    }

    // 중복 id인 유저가 이미 있는지 확인하는 함수
    fun isUserIdExists(id: String): Boolean {
        return userList.any { it.id == id }
    }

    // 회원가입 시 새로운 유저 데이터를 추가하는 함수
    fun addUser(newUser: UserData): Boolean {
        return if (!isUserIdExists(newUser.id)) {
            userList.add(newUser)
            db.child(newUser.id).setValue(newUser)
                .addOnSuccessListener {
                    Log.d("UserDataViewModel", "User data saved successfully.")
                }
                .addOnFailureListener { exception ->
                    Log.e("UserDataViewModel", "Failed to save user data.", exception)
                }
            true
        } else {
            false
        }
    }
}




/*
package com.example.fridgefriend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.fridgefriend.models.UserData

class UserDataViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance().getReference("users")
    val userList = mutableStateListOf<UserData>()
    var userIndex = mutableStateOf(0)

    init {
        // Realtime Database에서 데이터를 불러옴
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(UserData::class.java)
                    user?.let { userList.add(it) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
    }

    fun addExpireDateToContain(userIndex: Int, ingredientId: Int, expireDate: String) {
        userList[userIndex].contain[ingredientId] = expireDate
        saveUserDataToDatabase(userIndex)
    }

    fun removeExpireDateFromContain(userIndex: Int, ingredientId: Int) {
        userList[userIndex].contain.remove(ingredientId)
        saveUserDataToDatabase(userIndex)
    }

    private fun saveUserDataToDatabase(userIndex: Int) {
        val user = userList[userIndex]
        db.child(user.id).setValue(user)
    }
}


*/
/*
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
}*/

