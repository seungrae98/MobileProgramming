package com.example.fridgefriend.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fridgefriend.models.UserData
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserDataViewModel : ViewModel() {

    private val db = Firebase.database.getReference("userdata")
    var loggedInUserId: String? = null
        private set

    private val _containData = MutableLiveData<Map<String, String>>()
    val containData: LiveData<Map<String, String>> get() = _containData

    fun setLoggedInUserId(userId: String) {
        loggedInUserId = userId
    }

    var userIndex = mutableStateOf(0)
    var loginStatus = mutableStateOf(false)  // 로그인 상태


    // 현재 로그인된 유저 데이터 저장
    var currentUser = mutableStateOf<UserData?>(null)


    private fun fetchContainData() {
        loggedInUserId?.let { userId ->
            db.child(userId).child("contain").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.children.associate { it.key!! to it.getValue(String::class.java)!! }
                    _containData.value = data
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("UserDataViewModel", "Failed to fetch contain data", error.toException())
                }
            })
        }
    }

    fun addExpireDateToContain(ingredientId: Int, expireDate: String) {
        loggedInUserId?.let { userId ->
            db.child(userId).child("contain").child(ingredientId.toString()).setValue(expireDate)
        } ?: Log.e("UserDataViewModel", "UserId is null. Cannot add expire date.")
    }

    fun removeExpireDateFromContain(ingredientId: Int) {
        loggedInUserId?.let { userId ->
            db.child(userId).child("contain").child(ingredientId.toString()).removeValue()
        } ?: Log.e("UserDataViewModel", "UserId is null. Cannot remove expire date.")
    }


    private fun saveUserDataToDatabase(user: UserData) {
        db.child(user.id).setValue(user)
    }

    // checkInfo 함수: 입력받은 id와 pw를 가진 유저가 있는지 확인하고 결과를 Boolean으로 반환
    suspend fun checkInfo(id: String, pw: String): Boolean {
        return try {
            val snapshot = db.child(id).get().await()
            val user = snapshot.getValue(UserData::class.java)
            user?.pw == pw
        } catch (e: Exception) {
            Log.e("UserDataViewModel", "checkInfo: ", e)
            false
        }
    }

    // 중복 id인 유저가 이미 있는지 확인하는 함수
    fun isUserIdExists(id: String, callback: (Boolean) -> Unit) {
        db.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                callback(snapshot.exists())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("UserDataViewModel", "Failed to check if user ID exists.", error.toException())
                callback(false)
            }
        })
    }

    fun addUser(newUser: UserData, callback: (Boolean) -> Unit) {
        isUserIdExists(newUser.id) { exists ->
            if (!exists) {
                db.child(newUser.id).setValue(newUser)
                    .addOnSuccessListener {
                        Log.d("UserDataViewModel", "User added successfully: $newUser")
                        callback(true)
                    }
                    .addOnFailureListener { exception ->
                        Log.e("UserDataViewModel", "Failed to add user.", exception)
                        callback(false)
                    }
            } else {
                Log.d("UserDataViewModel", "User already exists: $newUser")
                callback(false)
            }
        }
    }

    fun getCurrentUserData(callback: (UserData?) -> Unit) {
        val userId = "user_id_here" // 현재 사용자 ID를 가져와서 설정
        db.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UserData::class.java)
                callback(user)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("UserDataViewModel", "Failed to fetch user data", error.toException())
                callback(null)
            }
        })
    }

}



//혹시몰라남겨둔과거의잔재들 근데깃허브써서 굳이 안이래도 됐네요 푸하하
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

