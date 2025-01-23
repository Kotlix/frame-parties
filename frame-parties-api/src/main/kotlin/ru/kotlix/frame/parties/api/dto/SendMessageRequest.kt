package ru.kotlix.frame.parties.api.dto

data class SendMessageRequest(
    val communityId: String,
    val chatId: String,
    val message: String
)
