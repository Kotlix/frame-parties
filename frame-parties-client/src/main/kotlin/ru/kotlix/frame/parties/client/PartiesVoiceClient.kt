package ru.kotlix.frame.parties.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import ru.kotlix.frame.parties.api.VoiceApi
import ru.kotlix.frame.parties.api.dto.entities.ConnectionGuide
import ru.kotlix.frame.parties.api.dto.entities.VoiceDto
import ru.kotlix.frame.parties.api.dto.requests.CreateVoiceRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateVoiceRequest

@FeignClient(name = "frame-parties-voice-client", path = "/api/v1")
interface PartiesVoiceClient : VoiceApi {
    @GetMapping("/community/{communityId}/voice")
    override fun getAllVoices(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
    ): List<VoiceDto>

    @GetMapping("/voice/{id}")
    override fun getVoiceById(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    ): VoiceDto

    @PostMapping("/community/{communityId}/voice")
    override fun createVoice(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
        @RequestBody
        request: CreateVoiceRequest,
    ): VoiceDto

    @PutMapping("/voice/{id}")
    override fun updateVoice(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
        @RequestBody
        request: UpdateVoiceRequest,
    ): VoiceDto

    @DeleteMapping("/voice/{id}")
    override fun deleteVoice(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    )

    @PostMapping("/voice/{id}/join")
    override fun joinVoice(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    ): ConnectionGuide

    @PostMapping("/voice/{id}/leave")
    override fun leaveVoice(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    )
}
