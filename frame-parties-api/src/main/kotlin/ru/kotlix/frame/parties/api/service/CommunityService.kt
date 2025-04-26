package ru.kotlix.frame.parties.api.service

import ru.kotlix.frame.parties.api.dto.entities.CommunityDto
import ru.kotlix.frame.parties.api.dto.entities.MembershipDto

interface CommunityService {
    fun getAllCommunities(): List<CommunityDto>
    fun getCommunityById(id: Long): CommunityDto
    fun createCommunity(dto: CommunityDto): CommunityDto
    fun updateCommunity(id: Long, dto: CommunityDto): CommunityDto
    fun deleteCommunity(id: Long)

    fun getMembers(communityId: Long): List<MembershipDto>
    fun joinCommunity(communityId: Long, userId: Long)
    fun leaveCommunity(communityId: Long, userId: Long)
}
