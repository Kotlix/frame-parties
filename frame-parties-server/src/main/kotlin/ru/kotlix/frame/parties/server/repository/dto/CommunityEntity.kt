package ru.kotlix.frame.parties.server.repository.dto

import java.time.OffsetDateTime

data class CommunityEntity(
    val id: Long? = null,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
    val name: String,
    val isPublic: Boolean,
    val description: String?,
)
