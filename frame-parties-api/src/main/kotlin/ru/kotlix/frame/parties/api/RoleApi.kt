package ru.kotlix.frame.parties.api

import ru.kotlix.frame.parties.api.dto.entities.RoleDto
import ru.kotlix.frame.parties.api.dto.requests.CreateRoleRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateRoleRequest

interface RoleApi {
    fun getAllRoles(communityId: Long): List<RoleDto>

    fun createRole(
        communityId: Long,
        request: CreateRoleRequest,
    ): RoleDto

    fun getRole(id: Long): RoleDto

    fun updateRole(
        id: Long,
        request: UpdateRoleRequest,
    ): RoleDto

    fun deleteRole(id: Long)
}
