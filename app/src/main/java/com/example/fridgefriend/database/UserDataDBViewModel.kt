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