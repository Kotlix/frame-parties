package ru.kotlix.frame.parties.server.service

import ru.kotlix.frame.parties.server.repository.dto.MembershipEntity
import ru.kotlix.frame.parties.server.repository.dto.MembershipRoleEntity
import ru.kotlix.frame.parties.server.repository.dto.RoleEntity
import ru.kotlix.frame.parties.server.service.dto.CommunityPermission

interface RoleService {
    fun getAllRoles(
        initiatorId: Long,
        communityId: Long,
    ): List<RoleEntity>

    fun createRole(
        initiatorId: Long,
        communityId: Long,
        roleName: String,
        priority: Int,
        rights: Map<String, Boolean>,
    ): RoleEntity

    fun getRole(
        initiatorId: Long,
        id: Long,
    ): RoleEntity

    fun updateRole(
        initiatorId: Long,
        id: Long,
        roleName: String,
        priority: Int,
        rights: Map<String, Boolean>,
    ): RoleEntity

    fun deleteRole(
        initiatorId: Long,
        id: Long,
    )

    fun assignRole(
        initiatorId: Long,
        targetId: Long,
        id: Long,
    )

    fun unassignRole(
        initiatorId: Long,
        targetId: Long,
        id: Long,
    )

    fun getPriority(membershipId: Long): Int

    fun hasRight(
        membershipId: Long,
        communityRight: CommunityPermission,
    ): Boolean

    fun createRole(
        communityId: Long,
        roleName: String,
        priority: Int,
        protected: Boolean,
        permissionSet: RoleEntity.PermissionSet,
    ): RoleEntity

    fun mapToPermissionSet(rights: Map<String, Boolean?>): RoleEntity.PermissionSet

    fun createDefaultUserRole(communityId: Long): RoleEntity

    fun createDefaultAdminRole(communityId: Long): RoleEntity

    fun findDefaultUserRole(communityId: Long): RoleEntity?

    fun deleteRole(role: RoleEntity)

    fun assignRole(
        membership: MembershipEntity,
        role: RoleEntity,
    ): MembershipRoleEntity

    fun unassignRole(
        membership: MembershipEntity,
        role: RoleEntity,
    )

    fun getUserRoles(
        initiatorId: Long,
        communityId: Long,
        targetId: Long,
    ): List<RoleEntity>
}
