package ru.kotlix.frame.parties.server.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.kotlix.frame.parties.api.CommunityApi
import ru.kotlix.frame.parties.api.dto.entities.CommunityDto
import ru.kotlix.frame.parties.api.dto.entities.InviteTokenDto
import ru.kotlix.frame.parties.api.dto.entities.MemberDto
import ru.kotlix.frame.parties.api.dto.requests.CreateCommunityRequest
import ru.kotlix.frame.parties.api.dto.requests.CreateTokenRequest
import ru.kotlix.frame.parties.api.dto.requests.FindPublicRequest
import ru.kotlix.frame.parties.api.dto.requests.JoinByTokenRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateCommunityRequest

@RestController
@RequestMapping("/api/v1")
class CommunityController() : CommunityApi {
    @GetMapping("/community/{communityId}")
    override fun getById(
        @PathVariable
        communityId: Long,
    ): CommunityDto = TODO()

    @PostMapping("/community")
    override fun create(
        @RequestBody
        dto: CreateCommunityRequest,
    ): CommunityDto = TODO()

    @PutMapping("/community/{communityId}")
    override fun update(
        @PathVariable
        communityId: Long,
        @RequestBody
        request: UpdateCommunityRequest,
    ): CommunityDto = TODO()

    @DeleteMapping("/community/{communityId}")
    override fun delete(
        @PathVariable
        communityId: Long,
    ) = TODO()

    @GetMapping("/communities")
    override fun findAllPublicWithFilter(
        @RequestBody
        request: FindPublicRequest,
    ): List<CommunityDto> = TODO()

    @GetMapping("/community-members/{communityId}")
    override fun getMembers(
        @PathVariable
        communityId: Long,
    ): List<MemberDto> = TODO()

    @PostMapping("/community-join")
    override fun joinCommunity(
        @RequestParam
        communityId: Long,
        @RequestParam
        userId: Long,
    ) = TODO()

    @PostMapping("/community-leave")
    override fun leaveCommunity(
        @RequestParam
        communityId: Long,
        @RequestParam
        userId: Long,
    ) = TODO()

    @PostMapping("/community-token/{communityId}")
    override fun createInviteToken(
        @PathVariable
        communityId: Long,
        @RequestBody
        request: CreateTokenRequest,
    ): InviteTokenDto = TODO()

    @PostMapping("/community-join")
    override fun joinByInviteToken(
        @RequestBody
        request: JoinByTokenRequest,
    ) = TODO()
}
