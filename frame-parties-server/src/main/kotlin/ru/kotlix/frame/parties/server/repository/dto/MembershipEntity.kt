package ru.kotlix.frame.parties.server.repository.dto

import java.time.OffsetDateTime

data class MembershipEntity(
    var id: Long? = null,
    var joinedAt: OffsetDateTime,
    var userId: Long,
    var communityId: Long,
)
