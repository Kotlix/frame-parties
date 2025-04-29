package ru.kotlix.frame.parties.server.repository.dto

import java.time.OffsetDateTime

data class MembershipRoleEntity(
    val id: Long? = null,
    val assignedAt: OffsetDateTime,
    val userId: Long,
    val communityId: Long,
    val roleId: Long,
)