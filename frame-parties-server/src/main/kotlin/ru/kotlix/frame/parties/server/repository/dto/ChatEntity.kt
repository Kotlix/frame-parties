package ru.kotlix.frame.parties.server.repository.dto

import java.time.OffsetDateTime

data class ChatEntity(
    val id: Long? = null,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
    val communityId: Long,
    val name: String,
    val parentDirectoryId: Long,
    val pos: Int
)
