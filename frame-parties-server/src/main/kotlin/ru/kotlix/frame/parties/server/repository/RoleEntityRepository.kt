package ru.kotlix.frame.parties.server.repository

import ru.kotlix.frame.parties.server.repository.dto.RoleEntity

interface RoleEntityRepository {
    fun findById(id: Long): RoleEntity?

    fun save(entity: RoleEntity): RoleEntity

    fun findAllByCommunityId(communityId: Long): List<RoleEntity>

    fun findAllByMembershipId(membershipId: Long): List<RoleEntity>
}
