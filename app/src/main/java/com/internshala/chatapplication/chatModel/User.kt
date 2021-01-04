package com.example.skynet.chatModel

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var name:String,
    var phone:String,
    var user_id:String
)