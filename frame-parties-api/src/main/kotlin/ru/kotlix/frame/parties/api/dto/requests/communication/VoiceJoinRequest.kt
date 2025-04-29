package ru.kotlix.frame.parties.api.dto.requests.communication

data class VoiceJoinRequest(
    val userId: Long,
    val elementId: Long
)
