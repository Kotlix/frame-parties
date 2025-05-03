package ru.kotlix.frame.parties.server.repository.dto

import java.time.OffsetDateTime

data class InvitationTokenEntity(
    var id: Long? = null,
    var createdAt: OffsetDateTime,
    var createdBy: Long,
    var token: String,
    var communityId: Long,
    var isOneTime: Boolean,
    val useCount: Int,
    var expiresAt: OffsetDateTime?,
)
