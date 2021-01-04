package com.example.skynet.chatModel

data class Message(
    var chat_id:String,
    var media_url:String,
    var message:String,
    var message_id:String,
    var sender_id:String,
    var timestamp:Long
)