package ru.kotlix.frame.parties.api.dto.requests.element

data class UpdateVoiceElementRequest(
    val title: String?,
    val bitrate: Int?,
    val userLimit: Int?
)
