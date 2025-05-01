package ru.kotlix.frame.parties.api.dto.requests

data class UpdateVoiceRequest(
    val name: String,
    val directoryId: Long,
    val order: Int,
)
