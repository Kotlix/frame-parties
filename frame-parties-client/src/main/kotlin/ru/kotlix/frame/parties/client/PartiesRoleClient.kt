package ru.kotlix.frame.parties.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import ru.kotlix.frame.parties.api.RoleApi
import ru.kotlix.frame.parties.api.dto.entities.RoleDto
import ru.kotlix.frame.parties.api.dto.requests.CreateRoleRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateRoleRequest

@FeignClient(name = "frame-parties-role-client", path = "/api/v1")
interface PartiesRoleClient : RoleApi {
    @GetMapping("/community/{communityId}/role")
    override fun getAllRoles(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
    ): List<RoleDto>

    @PostMapping("/community/{communityId}/role")
    override fun createRole(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
        @RequestBody
        request: CreateRoleRequest,
    ): RoleDto

    @GetMapping("/role/{id}")
    override fun getRole(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    ): RoleDto

    @PutMapping("/role/{id}")
    override fun updateRole(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
        @RequestBody
        request: UpdateRoleRequest,
    ): RoleDto

    @DeleteMapping("/role/{id}")
    override fun deleteRole(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    )

    @PutMapping("/role/{id}/assign/{targetId}")
    override fun assignRole(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("targetId")
        targetId: Long,
        @PathVariable("id")
        id: Long,
    )

    @PutMapping("/role/{id}/unassign/{targetId}")
    override fun unassignRole(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("targetId")
        targetId: Long,
        @PathVariable("id")
        id: Long,
    )
}
