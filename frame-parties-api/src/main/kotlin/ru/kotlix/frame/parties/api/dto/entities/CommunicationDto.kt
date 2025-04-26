package ru.kotlix.frame.parties.api.dto.entities

import java.time.LocalDateTime


class CommunicationDto(
    val messageId: Long?,
    val elementId: Long?,
    val senderId: Long?,
    val text: String?,
    val timestamp: LocalDateTime?,
)