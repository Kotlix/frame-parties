package ru.kotlix.frame.parties.api.dto

data class GetMessagesRequest(
    val communityId: String,
    val chatId: String,
    val from: Int,
    val limit: Int
)
