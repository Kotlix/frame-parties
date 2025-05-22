package ru.kotlix.frame.parties.server.mapper

import ru.kotlix.frame.parties.api.dto.entities.ChatDto
import ru.kotlix.frame.parties.api.dto.entities.CommunityDto
import ru.kotlix.frame.parties.api.dto.entities.ConnectionGuide
import ru.kotlix.frame.parties.api.dto.entities.DirectoryDto
import ru.kotlix.frame.parties.api.dto.entities.InviteTokenDto
import ru.kotlix.frame.parties.api.dto.entities.MemberDto
import ru.kotlix.frame.parties.api.dto.entities.MessageDto
import ru.kotlix.frame.parties.api.dto.entities.RoleDto
import ru.kotlix.frame.parties.api.dto.entities.VoiceDto
import ru.kotlix.frame.parties.server.repository.dto.ChatEntity
import ru.kotlix.frame.parties.server.repository.dto.CommunityEntity
import ru.kotlix.frame.parties.server.repository.dto.DirectoryEntity
import ru.kotlix.frame.parties.server.repository.dto.InvitationTokenEntity
import ru.kotlix.frame.parties.server.repository.dto.MembershipEntity
import ru.kotlix.frame.parties.server.repository.dto.RoleEntity
import ru.kotlix.frame.parties.server.repository.dto.TextMessageEntity
import ru.kotlix.frame.parties.server.repository.dto.VoiceEntity
import ru.kotlix.frame.parties.server.service.dto.CommunityPermission

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

fun RoleEntity.toRoleDto() =
    RoleDto(
        id = id!!,
        communityId = communityId,
        roleName = name,
        priority = priority,
        rights = permissionSet.toMap(),
    )

fun RoleEntity.PermissionSet.toMap(): Map<String, Boolean> =
    mapOf(
        CommunityPermission.SERVER_DELETE to serverDelete,
        CommunityPermission.SERVER_EDIT to serverEdit,
        CommunityPermission.SERVER_EDIT_ROLES to serverEditRoles,
        CommunityPermission.SERVER_EDIT_ELEMENTS to serverEditElements,
        CommunityPermission.SERVER_ASSIGN_ROLES to serverAssignRoles,
        CommunityPermission.SERVER_CREATE_INVITE to serverCreateInvite,
        CommunityPermission.CHAT_SEND_MESSAGES to chatSendMessages,
        CommunityPermission.VOICE_JOIN to voiceJoin,
    ).filter { kv -> kv.value != null }
        .mapKeys { kv -> kv.key.toString() }
        .mapValues { kv -> kv.value!! }

fun ru.kotlix.frame.voice.api.dto.ConnectionGuide.toPartiesConnectionGuideDto() =
    ConnectionGuide(
        hostAddress = hostAddress,
        secret = secret,
        channelId = channelId,
        shadowId = shadowId,
    )

fun VoiceEntity.toVoiceDto() =
    VoiceDto(
        id = id!!,
        communityId = communityId,
        name = name,
        directoryId = parentDirectoryId,
        order = pos,
    )
