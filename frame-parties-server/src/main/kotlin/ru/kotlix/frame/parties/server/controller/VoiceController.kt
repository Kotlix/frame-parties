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
import ru.kotlix.frame.parties.api.VoiceApi
import ru.kotlix.frame.parties.api.dto.entities.ConnectionGuide
import ru.kotlix.frame.parties.api.dto.entities.VoiceDto
import ru.kotlix.frame.parties.api.dto.requests.CreateVoiceRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateVoiceRequest
import ru.kotlix.frame.parties.server.exception.todoex
import ru.kotlix.frame.parties.server.exception.todoex1

@RestController
@RequestMapping("/api/v1")
class VoiceController() : VoiceApi {
    @GetMapping("/community/{communityId}/voice")
    override fun getAllVoices(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
    ): List<VoiceDto> = todoex()

    @GetMapping("/voice/{id}")
    override fun getVoiceById(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    ): VoiceDto = todoex()

    @PostMapping("/community/{communityId}/voice")
    override fun createVoice(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
        @RequestBody
        request: CreateVoiceRequest,
    ): VoiceDto = todoex()

    @PutMapping("/voice/{id}")
    override fun updateVoice(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
        @RequestBody
        request: UpdateVoiceRequest,
    ): VoiceDto = todoex()

    @DeleteMapping("/voice/{id}")
    override fun deleteVoice(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    ) = todoex1()

    @PostMapping("/voice-join")
    override fun joinVoice(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @RequestParam
        id: Long,
        @RequestParam
        userId: Long,
    ): ConnectionGuide = todoex()

    @PostMapping("/voice-leave")
    override fun leaveVoice(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @RequestParam
        id: Long,
        @RequestParam
        userId: Long,
    ) = todoex1()
}
