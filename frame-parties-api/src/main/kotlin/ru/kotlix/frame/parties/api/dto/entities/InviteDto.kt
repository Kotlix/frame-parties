package ru.kotlix.frame.parties.api.dto.entities;

import java.time.LocalDateTime


class InviteDto(
    val token: String,
    val communityId: Long,
    val expirationDate: LocalDateTime?,
    val isOneTime: Boolean?,
    val createdAt: LocalDateTime?,
    val createdBy: Long?
)