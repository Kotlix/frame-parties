package ru.kotlix.frame.parties.server.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.kotlix.frame.parties.api.MessageApi
import ru.kotlix.frame.parties.api.dto.entities.MessageDto
import ru.kotlix.frame.parties.api.dto.requests.FindMessagesRequest
import ru.kotlix.frame.parties.api.dto.requests.SendMessageRequest

@RestController
@RequestMapping("/api/v1")
class MessageController() : MessageApi {
    @PostMapping("/chat/{chatId}/send")
    override fun sendMessage(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("chatId")
        chatId: Long,
        @RequestBody
        request: SendMessageRequest,
    ): MessageDto = TODO()

    @GetMapping("/chat/{chatId}/all")
    override fun getMessages(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("chatId")
        chatId: Long,
        @RequestBody
        request: FindMessagesRequest,
    ): List<MessageDto> = TODO()

    @GetMapping("/chat-message/{id}")
    override fun getById(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        messageId: Long,
    ): MessageDto = TODO()
}
