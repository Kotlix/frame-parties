package ru.kotlix.frame.parties.api.dto.requests

data class CreateVoiceRequest(
    val name: String,
    val directoryId: Long,
    val order: Int,
)
