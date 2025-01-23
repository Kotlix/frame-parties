package ru.kotlix.frame.parties.api.dto.entities

data class Chat(
    val id: String,
    val communityId: String,
    val messageIds: List<String>?
)