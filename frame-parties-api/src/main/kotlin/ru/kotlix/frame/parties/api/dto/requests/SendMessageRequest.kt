package ru.kotlix.frame.parties.api.dto.requests

data class SendMessageRequest(
    val authorId: Long,
    val message: String,
)
