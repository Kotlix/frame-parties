package ru.kotlix.frame.parties.server.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kotlix.frame.parties.api.dto.entities.CommunityDto
import ru.kotlix.frame.parties.api.dto.entities.MembershipDto
import ru.kotlix.frame.parties.api.dto.oldrequests.CreateCommunityRequest
import ru.kotlix.frame.parties.api.dto.requests.community.*
import ru.kotlix.frame.parties.api.service.CommunityService

@RestController
@RequestMapping("/api/communities")
class CommunityController(
    private val communityService: CommunityService
) {

    @GetMapping("/public")
    fun getAllPublicWithFilter(
        @RequestParam(required = false) filter: String?
    ): ResponseEntity<List<Long>> =
        ResponseEntity.ok(communityService.getAllPublicCommunities(filter))


    @GetMapping("/{communityId}")
    fun getById(@PathVariable communityId: Long): ResponseEntity<CommunityDto> =
        ResponseEntity.ok(communityService.getCommunityById(communityId))

    @PostMapping
    fun create(@RequestBody dto: CreateCommunityRequest): ResponseEntity<CommunityDto> {
        val created = communityService.createCommunity(dto)
        return ResponseEntity.ok().body(created)
    }

    @PutMapping("/{communityId}/chat")
    fun update(@PathVariable communityId: Long, @RequestBody request: UpdateCommunityChatRequest): ResponseEntity<CommunityDto> =
        ResponseEntity.ok(communityService.updateCommunity(communityId, request))

    @DeleteMapping("/{communityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable communityId: Long) {
        communityService.deleteCommunity(communityId)
    }

    @GetMapping("/members/{communityId}")
    fun getMembers(@PathVariable communityId: Long): ResponseEntity<List<MembershipDto>> =
        ResponseEntity.ok(communityService.getMembers(communityId))

    @PostMapping("/{communityId}/{userId}")
    fun joinCommunity(
        @PathVariable communityId: Long,
        @PathVariable userId: Long
    ): ResponseEntity<Boolean> {
        communityService.joinCommunity(communityId, userId)
        return ResponseEntity.ok().build()
    }
    @PostMapping("/join/token")
    fun joinByToken(@RequestBody request: JoinByTokenRequest): ResponseEntity<Void> {
        communityService.joinCommunityByToken(request.token, request.userId)
        return ResponseEntity.ok().build()
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