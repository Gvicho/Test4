package com.example.test4

data class Person(val id:Int,
                  val name:String,
                  var lastMessage:String,
                  var lastMessageType: Message) {

}

enum class Message(val type_id:Int){
    TEXT(1),
    FILE(2),
    VOICE(3)
}