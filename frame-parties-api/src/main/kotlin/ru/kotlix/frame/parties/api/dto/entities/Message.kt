package ru.kotlix.frame.parties.api.dto.entities

data class Message(
    val id: Int,
    val text: String,
    val senderId: String
)
