package ru.kotlix.frame.parties.server.repository.dto

import java.time.OffsetDateTime

data class TextMessageEntity(
    val id: Long? = null,
    val createdAt: OffsetDateTime,
    val chatId: Long,
    val userId: Long,
    val message: String,
)
