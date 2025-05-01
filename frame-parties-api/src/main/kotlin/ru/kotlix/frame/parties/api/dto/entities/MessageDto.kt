package ru.kotlix.frame.parties.api.dto.entities

import java.time.LocalDateTime

class MessageDto(
    val id: Long,
    val chatId: Long,
    val authorId: Long,
    val message: String,
    val createdAt: LocalDateTime,
)
