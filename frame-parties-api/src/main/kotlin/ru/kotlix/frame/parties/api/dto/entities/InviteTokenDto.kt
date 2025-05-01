package ru.kotlix.frame.parties.api.dto.entities

import java.time.OffsetDateTime

class InviteTokenDto(
    val token: String,
    val expiresAt: OffsetDateTime,
    val isOneTime: Boolean,
)
