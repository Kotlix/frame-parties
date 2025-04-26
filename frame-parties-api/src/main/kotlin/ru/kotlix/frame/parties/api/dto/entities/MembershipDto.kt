package ru.kotlix.frame.parties.api.dto.entities

import java.time.LocalDateTime

data class MembershipDto(
    val communityId: Long,
    val userId: Long,
    val joinedAt: LocalDateTime?
)