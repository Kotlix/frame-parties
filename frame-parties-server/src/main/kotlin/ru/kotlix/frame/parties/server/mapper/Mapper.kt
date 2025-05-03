package ru.kotlix.frame.parties.server.mapper

import ru.kotlix.frame.parties.api.dto.entities.CommunityDto
import ru.kotlix.frame.parties.api.dto.entities.InviteTokenDto
import ru.kotlix.frame.parties.api.dto.entities.MemberDto
import ru.kotlix.frame.parties.server.repository.dto.CommunityEntity
import ru.kotlix.frame.parties.server.repository.dto.InvitationTokenEntity
import ru.kotlix.frame.parties.server.repository.dto.MembershipEntity

fun CommunityEntity.toCommunityDto() =
    CommunityDto(
        id = id!!,
        name = name,
        description = description,
        isPublic = isPublic,
    )

fun MembershipEntity.toMembershipDto() =
    MemberDto(
        userId = userId,
    )

fun InvitationTokenEntity.toInviteTokenDto() =
    InviteTokenDto(
        token = token,
        isOneTime = isOneTime,
        expiresAt = expiresAt,
    )
