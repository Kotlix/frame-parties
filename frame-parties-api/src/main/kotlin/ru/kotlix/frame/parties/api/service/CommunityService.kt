package ru.kotlix.frame.parties.api.service

import ru.kotlix.frame.parties.api.dto.entities.CommunityDto
import ru.kotlix.frame.parties.api.dto.entities.MembershipDto
import ru.kotlix.frame.parties.api.dto.oldrequests.CreateCommunityRequest
import ru.kotlix.frame.parties.api.dto.requests.community.*

interface CommunityService {
    fun getAllPublicCommunities(filter: String?): List<Long>
    fun getCommunityById(id: Long): CommunityDto
    fun createCommunity(request: CreateCommunityRequest): CommunityDto
    fun updateCommunity(id: Long, request: UpdateCommunityChatRequest): CommunityDto

    fun deleteCommunity(id: Long)

    fun getMembers(communityId: Long): List<MembershipDto>
    fun joinCommunity(communityId: Long, userId: Long)
    fun joinCommunityByToken(token: String, userId: Long)
    fun leaveCommunity(communityId: Long, userId: Long)
}
