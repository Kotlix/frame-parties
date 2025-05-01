package ru.kotlix.frame.parties.api.dto.requests

import java.time.OffsetDateTime

data class CreateTokenRequest(
    val userId: Long,
    val expiresAt: OffsetDateTime?,
    val isOneTime: Boolean,
)
