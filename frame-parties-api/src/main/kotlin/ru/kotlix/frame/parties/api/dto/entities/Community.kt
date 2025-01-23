package ru.kotlix.frame.parties.api.dto.entities

data class Community(
    val id: String,
    val name: String,
    val desc: String,
    val members: List<String>?,
    val chats: List<String>?,
    val channels: List<String>?,
    val voiceChats: List<String>?,
    val roles: List<Role>?
)
