package ru.kotlix.frame.parties.server.service

import ru.kotlix.frame.parties.server.repository.dto.CommunityEntity
import ru.kotlix.frame.parties.server.repository.dto.InvitationTokenEntity
import ru.kotlix.frame.parties.server.repository.dto.MembershipEntity
import java.time.OffsetDateTime

interface CommunityService {
    fun getById(
        initiatorId: Long,
        communityId: Long,
    ): CommunityEntity

    fun createCommunity(
        initiatorId: Long,
        name: String,
        desc: String?,
        isPublic: Boolean,
        voiceRegion: String,
        voiceName: String,
    ): CommunityEntity

    fun update(
        initiatorId: Long,
        communityId: Long,
        name: String,
        desc: String?,
        isPublic: Boolean,
        voiceRegion: String,
        voiceName: String,
    ): CommunityEntity

    fun delete(
        initiatorId: Long,
        communityId: Long,
    )

    fun findAllPublicWithFilter(
        initiatorId: Long,
        name: String?,
        pageOffset: Long,
        pageSize: Long,
    ): List<CommunityEntity>

    fun findAllByUserId(userId: Long): List<CommunityEntity>

    fun getMembers(
        initiatorId: Long,
        communityId: Long,
    ): List<MembershipEntity>

    fun joinCommunity(
        initiatorId: Long,
        communityId: Long,
    )

    fun joinCommunityByInviteToken(
        initiatorId: Long,
        token: String,
    )

    fun leaveCommunity(
        initiatorId: Long,
        communityId: Long,
    )

    fun createInviteToken(
        initiatorId: Long,
        communityId: Long,
        expiresAt: OffsetDateTime?,
        isOneTime: Boolean,
    ): InvitationTokenEntity
}
