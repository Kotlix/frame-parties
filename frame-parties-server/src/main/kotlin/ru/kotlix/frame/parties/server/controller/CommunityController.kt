package ru.kotlix.frame.parties.server.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kotlix.frame.parties.api.dto.entities.CommunityDto
import ru.kotlix.frame.parties.api.dto.entities.MembershipDto
import ru.kotlix.frame.parties.api.service.CommunityService

@RestController
@RequestMapping("/api/communities")
class CommunityController(
    private val communityService: CommunityService
) {

    @GetMapping
    fun getAll(): ResponseEntity<List<CommunityDto>> =
        ResponseEntity.ok(communityService.getAllCommunities())

    @GetMapping("/{communityId}")
    fun getById(@PathVariable communityId: Long): ResponseEntity<CommunityDto> =
        ResponseEntity.ok(communityService.getCommunityById(communityId))

    @PostMapping
    fun create(@RequestBody dto: CommunityDto): ResponseEntity<CommunityDto> {
        val created = communityService.createCommunity(dto)
        return ResponseEntity.ok().body(created)
    }

    @PutMapping("/{communityId}")
    fun update(
        @PathVariable communityId: Long,
        @RequestBody dto: CommunityDto
    ): ResponseEntity<CommunityDto> =
        ResponseEntity.ok(communityService.updateCommunity(communityId, dto))

    @DeleteMapping("/{communityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable communityId: Long) {
        communityService.deleteCommunity(communityId)
    }

    @GetMapping
    fun getMembers(@PathVariable communityId: Long): ResponseEntity<List<MembershipDto>> =
        ResponseEntity.ok(communityService.getMembers(communityId))

    @PostMapping("/{userId}")
    fun joinCommunity(
        @PathVariable communityId: Long,
        @PathVariable userId: Long
    ): ResponseEntity<Boolean> {
        communityService.joinCommunity(communityId, userId)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun leaveCommunity(
        @PathVariable communityId: Long,
        @PathVariable userId: Long
    ) {
        communityService.leaveCommunity(communityId, userId)
    }
}