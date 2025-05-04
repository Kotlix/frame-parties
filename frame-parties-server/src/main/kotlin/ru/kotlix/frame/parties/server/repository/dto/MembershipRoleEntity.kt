package ru.kotlix.frame.parties.server.repository.dto

import java.time.OffsetDateTime

data class MembershipRoleEntity(
    var id: Long? = null,
    var assignedAt: OffsetDateTime,
    var membershipId: Long,
    var roleId: Long,
)
