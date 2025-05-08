package ru.kotlix.frame.parties.server.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.kotlix.frame.parties.api.CommunityApi
import ru.kotlix.frame.parties.api.dto.entities.CommunityDto
import ru.kotlix.frame.parties.api.dto.entities.InviteTokenDto
import ru.kotlix.frame.parties.api.dto.entities.MemberDto
import ru.kotlix.frame.parties.api.dto.requests.CreateCommunityRequest
import ru.kotlix.frame.parties.api.dto.requests.CreateTokenRequest
import ru.kotlix.frame.parties.api.dto.requests.JoinByTokenRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateCommunityRequest
import ru.kotlix.frame.parties.server.mapper.toCommunityDto
import ru.kotlix.frame.parties.server.mapper.toInviteTokenDto
import ru.kotlix.frame.parties.server.mapper.toMembershipDto
import ru.kotlix.frame.parties.server.service.CommunityService

@RestController
@RequestMapping("/api/v1")
class CommunityController(
    private val communityService: CommunityService,
) : CommunityApi {
    @GetMapping("/community/{communityId}")
    override fun getById(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
    ): CommunityDto =
        communityService.getById(
            initiatorId,
            communityId,
        ).toCommunityDto()

    @PostMapping("/community")
    override fun create(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @RequestBody
        request: CreateCommunityRequest,
    ): CommunityDto =
        communityService.createCommunity(
            initiatorId,
            request.name,
            request.desc,
            request.isPublic,
            request.voiceRegion,
            request.voiceName,
        ).toCommunityDto()

    @PutMapping("/community/{communityId}")
    override fun update(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
        @RequestBody
        request: UpdateCommunityRequest,
    ): CommunityDto =
        communityService.update(
            initiatorId,
            communityId,
            request.name,
            request.desc,
            request.isPublic,
            request.voiceRegion,
            request.voiceName,
        ).toCommunityDto()

    @DeleteMapping("/community/{communityId}")
    override fun delete(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
    ) = communityService.delete(initiatorId, communityId)

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
    ): List<CommunityDto> =
        communityService.findAllPublicWithFilter(
            initiatorId,
            name,
            page,
            size,
        ).map { it.toCommunityDto() }

    @GetMapping("/my-communities")
    override fun findAllByUserId(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
    ): List<CommunityDto> =
        communityService.findAllByUserId(
            initiatorId,
        ).map { it.toCommunityDto() }

    @GetMapping("/community/{communityId}/members")
    override fun getMembers(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
    ): List<MemberDto> =
        communityService.getMembers(
            initiatorId,
            communityId,
        ).map { it.toMembershipDto() }

    @PostMapping("/community/{communityId}/join")
    override fun joinCommunity(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
    ) = communityService.joinCommunity(
        initiatorId,
        communityId,
    )

    @PostMapping("/community/{communityId}/leave")
    override fun leaveCommunity(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
    ) = communityService.leaveCommunity(
        initiatorId,
        communityId,
    )

    @PostMapping("/community/{communityId}/token")
    override fun createInviteToken(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
        @RequestBody
        request: CreateTokenRequest,
    ): InviteTokenDto =
        communityService.createInviteToken(
            initiatorId,
            communityId,
            request.expiresAt,
            request.isOneTime,
        ).toInviteTokenDto()

    @PostMapping("/community-join-token")
    override fun joinByInviteToken(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @RequestBody
        request: JoinByTokenRequest,
    ) = communityService.joinCommunityByInviteToken(
        initiatorId,
        request.token,
    )
}
