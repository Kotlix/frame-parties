package ru.kotlix.frame.parties.api

import ru.kotlix.frame.parties.api.dto.entities.RoleDto
import ru.kotlix.frame.parties.api.dto.requests.CreateRoleRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateRoleRequest

interface RoleApi {
    fun getAllRoles(
        initiatorId: Long,
        communityId: Long,
    ): List<RoleDto>

    fun createRole(
        initiatorId: Long,
        communityId: Long,
        request: CreateRoleRequest,
    ): RoleDto

    fun getRole(
        initiatorId: Long,
        id: Long,
    ): RoleDto

    fun updateRole(
        initiatorId: Long,
        id: Long,
        request: UpdateRoleRequest,
    ): RoleDto

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

    fun getUserRoles(
        initiatorId: Long,
        communityId: Long,
        targetId: Long,
    ): List<RoleDto>
}
