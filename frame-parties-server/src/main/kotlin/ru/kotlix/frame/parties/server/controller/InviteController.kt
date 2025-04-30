package ru.kotlix.frame.parties.server.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.kotlix.frame.parties.api.dto.entities.InviteDto
import ru.kotlix.frame.parties.api.service.InviteService

@RestController
@RequestMapping("/api/communities/{communityId}/invites")
class InviteController(
    private val inviteService: InviteService
) {

    @PostMapping
    fun create(
        @PathVariable communityId: Long,
        @RequestBody dto: InviteDto
    ): ResponseEntity<InviteDto> {
        val created = inviteService.createInvite(dto)
        return ResponseEntity.ok().body(created)
    }

    @GetMapping("/{token}")
    fun get(
        @PathVariable token: String
    ): ResponseEntity<InviteDto> {
        val invite = inviteService.getInvite(token)
        return ResponseEntity.ok(invite)
    }

    @DeleteMapping("/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun revoke(
        @PathVariable token: String
    ) {
        inviteService.revokeInvite(token)
    }
}