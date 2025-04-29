package ru.kotlix.frame.parties.server.repository.dto

import java.time.OffsetDateTime

data class MembershipEntity(
    val id: Long? = null,
    val joinedAt: OffsetDateTime,
    val userId: Long,
    val communityId: Long,
)
