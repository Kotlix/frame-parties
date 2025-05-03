package ru.kotlix.frame.parties.api.dto.entities

import java.time.OffsetDateTime

class InviteTokenDto(
    val token: String,
    val isOneTime: Boolean,
    val expiresAt: OffsetDateTime?,
)
