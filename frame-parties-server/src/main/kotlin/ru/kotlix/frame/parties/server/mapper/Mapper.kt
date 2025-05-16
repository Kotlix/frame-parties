package ru.kotlix.frame.parties.server.mapper

import ru.kotlix.frame.parties.api.dto.entities.ChatDto
import ru.kotlix.frame.parties.api.dto.entities.CommunityDto
import ru.kotlix.frame.parties.api.dto.entities.DirectoryDto
import ru.kotlix.frame.parties.api.dto.entities.InviteTokenDto
import ru.kotlix.frame.parties.api.dto.entities.MemberDto
import ru.kotlix.frame.parties.api.dto.entities.MessageDto
import ru.kotlix.frame.parties.server.repository.dto.ChatEntity
import ru.kotlix.frame.parties.server.repository.dto.CommunityEntity
import ru.kotlix.frame.parties.server.repository.dto.DirectoryEntity
import ru.kotlix.frame.parties.server.repository.dto.InvitationTokenEntity
import ru.kotlix.frame.parties.server.repository.dto.MembershipEntity
import ru.kotlix.frame.parties.server.repository.dto.TextMessageEntity

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

fun DirectoryEntity.toDirectoryDto() =
    DirectoryDto(
        id = id!!,
        communityId = communityId,
        name = name,
        directoryId = parentDirectoryId,
        order = pos,
    )

fun ChatEntity.toChatDto() =
    ChatDto(
        id = id!!,
        communityId = communityId,
        name = name,
        directoryId = parentDirectoryId,
        order = pos,
    )

fun TextMessageEntity.toMessageDto() =
    MessageDto(
        id = id!!,
        chatId = chatId,
        authorId = userId,
        createdAt = createdAt.toLocalDateTime(),
        message = message,
    )
