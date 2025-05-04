package ru.kotlix.frame.parties.server.repository

import ru.kotlix.frame.parties.server.repository.dto.MembershipEntity

interface MembershipEntityRepository {
    fun findById(id: Long): MembershipEntity?

    fun save(entity: MembershipEntity): MembershipEntity

    fun findAllByUserId(userId: Long): List<MembershipEntity>

    fun findAllByCommunityId(communityId: Long): List<MembershipEntity>

    fun remove(entity: MembershipEntity)
}
