package com.example.fridgefriend.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserDataViewModelFactory(private val repository: Repository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDataDBViewModel::class.java)) {
            return UserDataDBViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class UserDataDBViewModel (private val repository: Repository) : ViewModel(){

    private var _itemList = MutableStateFlow<List<UserDataDB>>(emptyList())
    val userList = _itemList.asStateFlow()

    init {
//        val userDataDBSample2 = UserDataDB(
//            "id2",
//            "1234",
//            "user2",
//            listOf<Int>(102, 105),
//            mapOf<String, String>("102" to "memo for card2 from user2", "103" to "memo for card3 from user2", "104" to "memo for card4 from user2"),
//            mapOf<String, String>("203" to "20240601", "204" to "20240602")
//        )
//        insertItem(userDataDBSample2)
//
//        val userDataDBSample3 = UserDataDB(
//            "id3",
//            "1234",
//            "user3",
//            listOf<Int>(101, 103, 105),
//            mapOf<String, String>("101" to "memo for card1 from user3", "104" to "memo for card4 from user3"),
//            mapOf<String, String>("201" to "20240530", "202" to "20240531", "203" to "20240606")
//        )
//        insertItem(userDataDBSample3)

        getAllItems()
    }

    fun insertItem(item: UserDataDB) {
        viewModelScope.launch {
            repository.insertItem(item)
            getAllItems()
        }
    }


    fun updateItem(item: UserDataDB) {
        viewModelScope.launch {
            repository.updateItem(item)
            getAllItems()
        }
    }

    fun deleteItem(item: UserDataDB) {
        viewModelScope.launch {
            repository.deleteItem(item)
            getAllItems()
        }
    }

    fun getAllItems() {
        viewModelScope.launch {
            repository.getAllItems().collect{
                _itemList.value = it
            }
        }
    }

    fun findItem(itemName: String) {
        viewModelScope.launch {
            repository.findItem(itemName).collect {
                _itemList.value = it
            }
        }
    }

}