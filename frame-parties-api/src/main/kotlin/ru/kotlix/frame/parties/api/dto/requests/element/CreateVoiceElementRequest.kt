package ru.kotlix.frame.parties.api.dto.requests.element

data class CreateVoiceElementRequest(
    val title: String,
    val bitrate: Int?,
    val userLimit: Int?
)