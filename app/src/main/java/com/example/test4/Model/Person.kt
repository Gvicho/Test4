package com.example.test4.Model

import com.example.test4.R

data class Person(val id:Int,
                  val image:String?,
                  val owner:String,
                  var last_message:String,
                  val last_active:String,
                  val unread_messages:Int,
                  val is_typing:Boolean,
                  var laste_message_type:String
) {

    private var messageType: Message = Message.TEXT
    var hasImage:Boolean = true
    var hasUnreadMessages:Boolean = false

    init {
        if(laste_message_type == "file") messageType = Message.FILE
        else if(laste_message_type == "voice") messageType = Message.VOICE

        if(image.isNullOrEmpty()) hasImage = false

        if(unread_messages != 0) hasUnreadMessages = true
    }

    fun isLastMessageText():Boolean{
        return messageType==Message.TEXT
    }

    fun getImageForLastMessageType():Int{
        return when(messageType){
            Message.FILE -> R.drawable.ic_file_17
            Message.VOICE -> R.drawable.ic_voice_17
            else -> 0
        }
    }

    fun getTextForLastMessageType():String{
        return when(messageType){
            Message.FILE -> Message.FILE.description
            Message.VOICE -> Message.VOICE.description
            else -> last_message
        }
    }

    fun getImageUrl():String{
        return image!!  // first will check hasImage, so there is no way image will be null or empty here
    }

}

enum class Message(val description: String){
    TEXT(""),
    FILE("Sent An Attachment"),
    VOICE("Sent a voice message")
}