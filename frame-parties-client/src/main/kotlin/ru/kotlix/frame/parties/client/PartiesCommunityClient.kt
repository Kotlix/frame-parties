package ru.kotlix.frame.parties.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import ru.kotlix.frame.parties.api.CommunityApi
import ru.kotlix.frame.parties.api.dto.entities.CommunityDto
import ru.kotlix.frame.parties.api.dto.entities.InviteTokenDto
import ru.kotlix.frame.parties.api.dto.entities.MemberDto
import ru.kotlix.frame.parties.api.dto.requests.CreateCommunityRequest
import ru.kotlix.frame.parties.api.dto.requests.CreateTokenRequest
import ru.kotlix.frame.parties.api.dto.requests.JoinByTokenRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateCommunityRequest

@FeignClient(name = "frame-parties-community-client", path = "/api/v1")
interface PartiesCommunityClient : CommunityApi {
    @GetMapping("/community/{communityId}")
    override fun getById(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
    ): CommunityDto

    @PostMapping("/community")
    override fun create(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @RequestBody
        request: CreateCommunityRequest,
    ): CommunityDto

    @PutMapping("/community/{communityId}")
    override fun update(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
        @RequestBody
        request: UpdateCommunityRequest,
    ): CommunityDto

    @DeleteMapping("/community/{communityId}")
    override fun delete(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
    )

    @GetMapping("/all-communities")
    override fun findAllPublicWithFilter(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @RequestParam("q", required = false)
        name: String?,
        @RequestParam("page")
        page: Long,
        @RequestParam("size")
        size: Long,
    ): List<CommunityDto>

    @GetMapping("/my-communities")
    override fun findAllByUserId(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
    ): List<CommunityDto>

    @GetMapping("/community/{communityId}/members")
    override fun getMembers(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
    ): List<MemberDto>

    @PostMapping("/community/{communityId}/join")
    override fun joinCommunity(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
    )

    @PostMapping("/community/{communityId}/leave")
    override fun leaveCommunity(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
    )

    @PostMapping("/community/{communityId}/token")
    override fun createInviteToken(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
        @RequestBody
        request: CreateTokenRequest,
    ): InviteTokenDto

    @PostMapping("/community-join-token")
    override fun joinByInviteToken(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @RequestBody
        request: JoinByTokenRequest,
    )
}
