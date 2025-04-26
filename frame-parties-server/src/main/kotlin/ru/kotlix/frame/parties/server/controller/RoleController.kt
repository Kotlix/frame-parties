package ru.kotlix.frame.parties.server.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kotlix.frame.parties.api.dto.entities.RoleDto
import ru.kotlix.frame.parties.api.service.RoleService

@RestController
@RequestMapping("/api/communities/{communityId}/roles")
class RoleController(
    private val roleService: RoleService
) {

    @GetMapping
    fun getAll(@PathVariable communityId: Long): ResponseEntity<List<RoleDto>> =
        ResponseEntity.ok(roleService.getRolesByCommunityId(communityId))

    @PostMapping
    fun create(
        @PathVariable communityId: Long,
        @RequestBody dto: RoleDto
    ): ResponseEntity<RoleDto> {
        val created = roleService.createRole(communityId, dto)
        return ResponseEntity.ok().body(created)
    }

    @PutMapping("/{roleId}")
    fun edit(
        @PathVariable communityId: Long,
        @PathVariable roleId: Long,
        @RequestBody dto: RoleDto
    ): ResponseEntity<RoleDto> =
        ResponseEntity.ok(roleService.updateRole(communityId, roleId, dto))

    @DeleteMapping("/{roleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable communityId: Long,
        @PathVariable roleId: Long
    ) {
        roleService.deleteRole(communityId, roleId)
    }
}