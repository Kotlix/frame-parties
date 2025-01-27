package ru.kotlix.frame.parties.api.dto.entities

data class Chat(
    val id: Int,
    val communityId: String,
    val messageIds: List<String>?
)