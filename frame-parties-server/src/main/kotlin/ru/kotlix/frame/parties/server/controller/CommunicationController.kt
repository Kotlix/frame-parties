package ru.kotlix.frame.parties.server.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kotlix.frame.parties.api.dto.entities.CommunicationResponse
import ru.kotlix.frame.parties.api.dto.requests.communication.CommunicationSendMessageRequest
import ru.kotlix.frame.parties.api.dto.requests.communication.VoiceJoinRequest
import ru.kotlix.frame.parties.api.service.CommunicationService

@RestController
@RequestMapping("/api/elements/{elementId}/communication")
class CommunicationController(
    private val communicationService: CommunicationService
) {

    @PostMapping
    fun send(
        @RequestBody message: CommunicationSendMessageRequest
    ): ResponseEntity<CommunicationResponse> {
        val created = communicationService.sendMessage(message)
        return ResponseEntity.ok().body(created)
    }

    @GetMapping("/all")
    fun getAll(@PathVariable communityID: Long,
               @PathVariable offset: Long,
               @PathVariable number: Long,
    ): ResponseEntity<List<CommunicationResponse>> =
        ResponseEntity.ok(communicationService.getMessagesByCommunityId(communityID, offset, number))

    @GetMapping("/{messageId}")
    fun getById(
        @PathVariable communityId: Long,
        @PathVariable elementId: Long,
        @PathVariable messageId: Long
    ): ResponseEntity<CommunicationResponse> =
        ResponseEntity.ok(communicationService.getMessageById(communityId, elementId, messageId))

    @PostMapping("/join")
    fun joinVoice(
        @PathVariable communityId: Long,
        @RequestBody request: VoiceJoinRequest
    ): ResponseEntity<String> {
        val sessionId = communicationService.joinVoiceChannel(communityId, request)
        return ResponseEntity.ok(sessionId)
    }

    @DeleteMapping("/leave")
    fun leaveVoice(
        @PathVariable communityId: Long,
        @RequestParam userId: Long
    ): ResponseEntity<Void> {
        communicationService.leaveVoiceChannel(communityId, userId)
        return ResponseEntity.noContent().build()
    }
}