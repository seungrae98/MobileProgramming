package com.example.fridgefriend.database

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class Repository(private val table: DatabaseReference){

    suspend fun insertItem(item: UserDataDB) {
        table.child(item.id).setValue(item)
    }

    suspend fun updateItem(item: UserDataDB) {
        table.child(item.id).setValue(item)
    }

    suspend fun deleteItem(item: UserDataDB) {
        table.child(item.id).removeValue()
    }

    fun getAllItems(): Flow<List<UserDataDB>> = callbackFlow {
        val listener = object:ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<UserDataDB>()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(UserDataDB::class.java)
                    item?.let {
                        itemList.add(it)
                    }
                }
                trySend(itemList)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        table.addValueEventListener(listener)
        awaitClose {
            table.removeEventListener(listener)
        }
    }

    fun findItem(userId: String): Flow<List<UserDataDB>> = callbackFlow {
        val listener = object:ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<UserDataDB>()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(UserDataDB::class.java)
                    item?.let {
                        if (it.id == userId) {
                            itemList.add(it)
                        }
                    }
                }
                trySend(itemList)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        table.addValueEventListener(listener)
        awaitClose {
            table.removeEventListener(listener)
        }
    }
}