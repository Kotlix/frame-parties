package ru.kotlix.frame.parties.server.repository

import ru.kotlix.frame.parties.server.repository.dto.MembershipRoleEntity

interface MembershipRoleEntityRepository {
    fun findById(id: Long): MembershipRoleEntity?

    fun save(entity: MembershipRoleEntity): MembershipRoleEntity

    fun findAllByUserIdAndCommunityId(userId: Long, communityId: Long): List<MembershipRoleEntity>
}