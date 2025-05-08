package ru.kotlix.frame.parties.api

import ru.kotlix.frame.parties.api.dto.entities.CommunityDto
import ru.kotlix.frame.parties.api.dto.entities.InviteTokenDto
import ru.kotlix.frame.parties.api.dto.entities.MemberDto
import ru.kotlix.frame.parties.api.dto.requests.CreateCommunityRequest
import ru.kotlix.frame.parties.api.dto.requests.CreateTokenRequest
import ru.kotlix.frame.parties.api.dto.requests.JoinByTokenRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateCommunityRequest

interface CommunityApi {
    fun getById(
        initiatorId: Long,
        communityId: Long,
    ): CommunityDto

    fun create(
        initiatorId: Long,
        request: CreateCommunityRequest,
    ): CommunityDto

    fun update(
        initiatorId: Long,
        communityId: Long,
        request: UpdateCommunityRequest,
    ): CommunityDto

    fun delete(
        initiatorId: Long,
        communityId: Long,
    )

    fun findAllPublicWithFilter(
        initiatorId: Long,
        name: String?,
        page: Long,
        size: Long,
    ): List<CommunityDto>

    fun findAllByUserId(initiatorId: Long): List<CommunityDto>

    fun getMembers(
        initiatorId: Long,
        communityId: Long,
    ): List<MemberDto>

    fun joinCommunity(
        initiatorId: Long,
        communityId: Long,
    )

    fun leaveCommunity(
        initiatorId: Long,
        communityId: Long,
    )

    fun createInviteToken(
        initiatorId: Long,
        communityId: Long,
        request: CreateTokenRequest,
    ): InviteTokenDto

    fun joinByInviteToken(
        initiatorId: Long,
        request: JoinByTokenRequest,
    )
}
