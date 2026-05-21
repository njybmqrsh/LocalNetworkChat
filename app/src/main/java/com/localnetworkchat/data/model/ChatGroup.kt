package com.localnetworkchat.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_groups")
data class ChatGroup(
    @PrimaryKey
    val groupId: String,
    val groupName: String,
    val isPublic: Boolean,
    val createdBy: String,
    val createdAt: Long = System.currentTimeMillis(),
    val description: String = "",
    val memberCount: Int = 0,
    val lastMessage: String = "",
    val lastMessageTime: Long = 0
)
