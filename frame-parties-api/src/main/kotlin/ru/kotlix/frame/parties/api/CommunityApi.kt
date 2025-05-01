package ru.kotlix.frame.parties.api

import ru.kotlix.frame.parties.api.dto.entities.CommunityDto
import ru.kotlix.frame.parties.api.dto.entities.InviteTokenDto
import ru.kotlix.frame.parties.api.dto.entities.MemberDto
import ru.kotlix.frame.parties.api.dto.requests.CreateCommunityRequest
import ru.kotlix.frame.parties.api.dto.requests.CreateTokenRequest
import ru.kotlix.frame.parties.api.dto.requests.FindPublicRequest
import ru.kotlix.frame.parties.api.dto.requests.JoinByTokenRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateCommunityRequest

interface CommunityApi {
    fun getById(communityId: Long): CommunityDto

    fun create(dto: CreateCommunityRequest): CommunityDto

    fun update(
        communityId: Long,
        request: UpdateCommunityRequest,
    ): CommunityDto

    fun delete(communityId: Long)

    fun findAllPublicWithFilter(request: FindPublicRequest): List<CommunityDto>

    fun getMembers(communityId: Long): List<MemberDto>

    fun joinCommunity(
        communityId: Long,
        userId: Long,
    )

    fun leaveCommunity(
        communityId: Long,
        userId: Long,
    )

    fun createInviteToken(
        communityId: Long,
        request: CreateTokenRequest,
    ): InviteTokenDto

    fun joinByInviteToken(request: JoinByTokenRequest)
}
