package ru.kotlix.frame.parties.server.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.kotlix.frame.parties.api.RoleApi
import ru.kotlix.frame.parties.api.dto.entities.RoleDto
import ru.kotlix.frame.parties.api.dto.requests.CreateRoleRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateRoleRequest
import ru.kotlix.frame.parties.server.mapper.toRoleDto
import ru.kotlix.frame.parties.server.service.RoleService

@RestController
@RequestMapping("/api/v1")
class RoleController(
    private val roleService: RoleService,
) : RoleApi {
    @GetMapping("/community/{communityId}/role")
    override fun getAllRoles(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
    ): List<RoleDto> = roleService.getAllRoles(initiatorId, communityId).map { it.toRoleDto() }

    @PostMapping("/community/{communityId}/role")
    override fun createRole(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
        @RequestBody
        request: CreateRoleRequest,
    ): RoleDto = roleService.createRole(initiatorId, communityId, request.roleName, request.priority, request.rights).toRoleDto()

    @GetMapping("/role/{id}")
    override fun getRole(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    ): RoleDto = roleService.getRole(initiatorId, id).toRoleDto()

    @PutMapping("/role/{id}")
    override fun updateRole(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
        @RequestBody
        request: UpdateRoleRequest,
    ): RoleDto = roleService.updateRole(initiatorId, id, request.roleName, request.priority, request.rights).toRoleDto()

    @DeleteMapping("/role/{id}")
    override fun deleteRole(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    ) = roleService.deleteRole(initiatorId, id)

    @PutMapping("/role/{id}/assign/{targetId}")
    override fun assignRole(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("targetId")
        targetId: Long,
        @PathVariable("id")
        id: Long,
    ) = roleService.assignRole(initiatorId, id, targetId)

    @PutMapping("/role/{id}/unassign/{targetId}")
    override fun unassignRole(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("targetId")
        targetId: Long,
        @PathVariable("id")
        id: Long,
    ) = roleService.unassignRole(initiatorId, id, targetId)

    @GetMapping("/community/{communityId}/user/{targetId}/role")
    override fun getUserRoles(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
        @PathVariable("targetId")
        targetId: Long,
    ) = roleService.getUserRoles(initiatorId, communityId, targetId).map { it.toRoleDto() }
}
