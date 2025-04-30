package ru.kotlix.frame.parties.server.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kotlix.frame.parties.api.dto.entities.MessageDto
import ru.kotlix.frame.parties.api.dto.requests.communication.SendMessageRequest
import ru.kotlix.frame.parties.api.service.MessageService

@RestController
@RequestMapping("/api/messages/{communityId}")
class MessageController(
    private val messageService: MessageService
) {

    @PostMapping
    fun sendMessage(
        @RequestBody message: SendMessageRequest
    ): ResponseEntity<MessageDto> {
        val created = messageService.sendMessage(message)
        return ResponseEntity.ok().body(created)
    }

    @GetMapping("/all/{chatId}/{offset}/{number}")
    fun getAllMessages(
        @PathVariable communityID: Long,
        @PathVariable chatId: Long,
        @PathVariable offset: Long,
        @PathVariable number: Long,
    ): ResponseEntity<List<MessageDto>> =
        ResponseEntity.ok(messageService.getMessagesByCommunityId(communityID, chatId, offset, number))

    @GetMapping("/l/{chatId}/{messageId}")
    fun getById(
        @PathVariable communityId: Long,
        @PathVariable chatId: Long,
        @PathVariable messageId: Long
    ): ResponseEntity<MessageDto> =
        ResponseEntity.ok(messageService.getMessageById(communityId, chatId, messageId))

}