package ru.kotlix.frame.parties.api.service

import ru.kotlix.frame.parties.api.dto.entities.RoleDto

interface RoleService {
    fun getRolesByCommunityId(communityId: Long): List<RoleDto>
    fun createRole(communityId: Long, dto: RoleDto): RoleDto
    fun updateRole(communityId: Long, roleId: Long, dto: RoleDto): RoleDto
    fun deleteRole(communityId: Long, roleId: Long): Unit
}