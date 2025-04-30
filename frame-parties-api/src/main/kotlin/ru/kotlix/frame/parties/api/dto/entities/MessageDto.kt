package ru.kotlix.frame.parties.api.dto.entities

import java.time.LocalDateTime


class MessageDto(
    val messageId: Long,
    val elementId: Long?,
    val senderId: Long?,
    val text: String?,
    val timestamp: LocalDateTime?,

    val communityId: Long?,
    val parentElementId: Long?,
    val type: String?,
    val name: String?,
    val order: Int?,
    val createdAt: LocalDateTime?
)