package ru.kotlix.frame.parties.server.service

import ru.kotlix.frame.parties.server.repository.dto.InvitationTokenEntity
import java.time.OffsetDateTime

interface TokenService {
    fun generateToken(
        initiatorId: Long,
        communityId: Long,
        isOneTime: Boolean,
        expiresAt: OffsetDateTime?,
    ): InvitationTokenEntity

    fun useToken(
        initiatorId: Long,
        token: String,
    )
}
