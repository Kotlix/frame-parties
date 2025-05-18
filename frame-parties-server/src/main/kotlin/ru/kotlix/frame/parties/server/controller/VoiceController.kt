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
import ru.kotlix.frame.parties.api.VoiceApi
import ru.kotlix.frame.parties.api.dto.entities.ConnectionGuide
import ru.kotlix.frame.parties.api.dto.entities.VoiceDto
import ru.kotlix.frame.parties.api.dto.requests.CreateVoiceRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateVoiceRequest
import ru.kotlix.frame.parties.server.mapper.toVoiceDto
import ru.kotlix.frame.parties.server.service.VoiceService

@RestController
@RequestMapping("/api/v1")
class VoiceController(
    val voiceService: VoiceService,
) : VoiceApi {
    @GetMapping("/community/{communityId}/voice")
    override fun getAllVoices(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
    ): List<VoiceDto> = voiceService.getAllVoices(initiatorId, communityId).map { it.toVoiceDto() }

    @GetMapping("/voice/{id}")
    override fun getVoiceById(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    ): VoiceDto = voiceService.getVoiceById(initiatorId, id).toVoiceDto()

    @PostMapping("/community/{communityId}/voice")
    override fun createVoice(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
        @RequestBody
        request: CreateVoiceRequest,
    ): VoiceDto = voiceService.createVoice(initiatorId, communityId, request.name, request.directoryId, request.order).toVoiceDto()

    @PutMapping("/voice/{id}")
    override fun updateVoice(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
        @RequestBody
        request: UpdateVoiceRequest,
    ): VoiceDto = voiceService.updateVoice(initiatorId, id, request.name, request.directoryId, request.order).toVoiceDto()

    @DeleteMapping("/voice/{id}")
    override fun deleteVoice(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    ) = voiceService.deleteVoice(initiatorId, id)

    @PostMapping("/voice/{id}/join")
    override fun joinVoice(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    ): ConnectionGuide = voiceService.joinVoice(initiatorId, id)

    @PostMapping("/voice/{id}/leave")
    override fun leaveVoice(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    ) = voiceService.leaveVoice(initiatorId, id)
}
