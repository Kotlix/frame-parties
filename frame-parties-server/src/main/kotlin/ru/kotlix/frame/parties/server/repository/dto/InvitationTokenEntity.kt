package ru.kotlix.frame.parties.server.repository.dto

import java.time.OffsetDateTime

data class InvitationTokenEntity(
    val id: Long? = null,
    val createdAt: OffsetDateTime,
    val createdBy: Long,
    val token: String,
    val communityId: Long,
    val isOneTime: Boolean,
    val expiresAt: OffsetDateTime?
)
