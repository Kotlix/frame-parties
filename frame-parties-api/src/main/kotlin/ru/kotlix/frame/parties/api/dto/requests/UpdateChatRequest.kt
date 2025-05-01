package ru.kotlix.frame.parties.api.dto.requests

data class UpdateChatRequest(
    val name: String,
    val directoryId: Long,
    val order: Int,
)
