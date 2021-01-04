package com.example.skynet.chatModel

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ChatModel {
    private lateinit var databaseReference: DatabaseReference

    private fun addNewUser(name:String,phone:String)
    {
        databaseReference.database.getReferenceFromUrl("https://chatapplication-5e5c5-default-rtdb.firebaseio.com/")
        val id=databaseReference.child("user").push().key
        val user=User(name,phone,id.toString())
        databaseReference.child("user").child("id").setValue(user)
    }
    fun sendMessage(){
        databaseReference.database.getReferenceFromUrl("https://chatapplication-5e5c5-default-rtdb.firebaseio.com/")
        val msgId=databaseReference.child("Messages").push().key
    }
}