package ru.kotlix.frame.parties.server.repository

import ru.kotlix.frame.parties.server.repository.dto.MembershipRoleEntity

interface MembershipRoleEntityRepository {
    fun findById(id: Long): MembershipRoleEntity?

    fun save(entity: MembershipRoleEntity): MembershipRoleEntity

    fun findAllByMembershipId(membershipId: Long): List<MembershipRoleEntity>

    fun remove(entity: MembershipRoleEntity)

    fun removeAllByMembershipId(membershipId: Long)
}
