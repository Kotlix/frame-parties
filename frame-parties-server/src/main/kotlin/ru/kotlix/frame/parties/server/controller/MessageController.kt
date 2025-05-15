package ru.kotlix.frame.parties.server.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.kotlix.frame.parties.api.MessageApi
import ru.kotlix.frame.parties.api.dto.entities.MessageDto
import ru.kotlix.frame.parties.api.dto.requests.SendMessageRequest
import ru.kotlix.frame.parties.server.mapper.toMessageDto
import ru.kotlix.frame.parties.server.service.MessageService

@RestController
@RequestMapping("/api/v1")
class MessageController(
    private val messageService: MessageService,
) : MessageApi {
    @PostMapping("/chat/{chatId}/send")
    override fun sendMessage(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("chatId")
        chatId: Long,
        @RequestBody
        request: SendMessageRequest,
    ): MessageDto = messageService.sendMessage(initiatorId, chatId, request.message).toMessageDto()

    @GetMapping("/chat/{chatId}/all")
    override fun getMessages(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("chatId")
        chatId: Long,
        @RequestParam("page")
        page: Long,
        @RequestParam("size")
        size: Long,
    ): List<MessageDto> = messageService.getMessages(initiatorId, chatId, page, size).map { it.toMessageDto() }

    @GetMapping("/chat-message/{id}")
    override fun getById(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        messageId: Long,
    ): MessageDto = messageService.getMessageById(initiatorId, messageId).toMessageDto()
}
