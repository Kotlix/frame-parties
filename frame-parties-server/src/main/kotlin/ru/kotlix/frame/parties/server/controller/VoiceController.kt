package ru.kotlix.frame.parties.server.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kotlix.frame.parties.api.dto.entities.VoiceChat
import ru.kotlix.frame.parties.api.dto.requests.voice.VoiceJoinRequest
import ru.kotlix.frame.parties.api.dto.requests.voice.VoiceCreateRequest
import ru.kotlix.frame.parties.api.dto.requests.voice.VoiceUpdateRequest
import ru.kotlix.frame.parties.api.service.VoiceService

@RestController
@RequestMapping("/api/communities/{communityId}/voice-chats")
class VoiceController(
    private val voiceService: VoiceService
) {
    @PostMapping("/create")
    fun createVoice(
        @PathVariable communityId: Long,
        @RequestBody request: VoiceCreateRequest
    ): ResponseEntity<String> {
        val sessionId = voiceService.createVoiceChat(communityId, request)
        return ResponseEntity.ok(sessionId)
    }

    @PostMapping("/update")
    fun updateVoice(
        @PathVariable communityId: Long,
        @RequestBody request: VoiceUpdateRequest
    ): ResponseEntity<String> {
        val sessionId = voiceService.updateVoiceChat(communityId, request)
        return ResponseEntity.ok(sessionId)
    }

    @GetMapping("/all")
    fun getAllVoiceChats(
        @PathVariable communityId: Long
    ): ResponseEntity<List<Long>> {
        val chats = voiceService.getAll(communityId)
        return ResponseEntity.ok(chats)
    }

    @GetMapping("/voice-chat/{voiceId}")
    fun getVoiceChatById(
        @PathVariable communityId: Long,
        @PathVariable voiceId: Long
    ): ResponseEntity<VoiceChat> {
        val chats = voiceService.getVoiceChatById(communityId, voiceId)
        return ResponseEntity.ok(chats)
    }

    @PostMapping("/join")
    fun joinVoice(
        @PathVariable communityId: Long,
        @RequestBody request: VoiceJoinRequest
    ): ResponseEntity<String> {
        val sessionId = voiceService.joinVoiceChat(communityId, request)
        return ResponseEntity.ok(sessionId)
    }

    @DeleteMapping("/leave")
    fun leaveVoice(
        @PathVariable communityId: Long,
        @RequestParam userId: Long
    ): ResponseEntity<Void> { // покидает все чаты?
        voiceService.leaveVoiceChat(communityId, userId)
        return ResponseEntity.noContent().build()
    }
}