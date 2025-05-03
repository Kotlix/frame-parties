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

@RestController
@RequestMapping("/api/v1")
class RoleController() : RoleApi {
    @GetMapping("/community/{communityId}/role")
    override fun getAllRoles(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
    ): List<RoleDto> = TODO()

    @PostMapping("/community/{communityId}/role")
    override fun createRole(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
        @RequestBody
        request: CreateRoleRequest,
    ): RoleDto = TODO()

    @GetMapping("/role/{id}")
    override fun getRole(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    ): RoleDto = TODO()

    @PutMapping("/role/{id}")
    override fun updateRole(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
        @RequestBody
        request: UpdateRoleRequest,
    ): RoleDto = TODO()

    @DeleteMapping("/role/{id}")
    override fun deleteRole(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    ) = TODO()
}
