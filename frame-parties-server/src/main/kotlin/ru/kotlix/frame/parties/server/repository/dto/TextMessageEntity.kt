package ru.kotlix.frame.parties.server.repository.dto

import java.time.OffsetDateTime

data class TextMessageEntity(
    var id: Long? = null,
    var createdAt: OffsetDateTime,
    var chatId: Long,
    var userId: Long,
    var message: String,
)
