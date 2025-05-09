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
import ru.kotlix.frame.parties.api.ChatApi
import ru.kotlix.frame.parties.api.dto.entities.ChatDto
import ru.kotlix.frame.parties.api.dto.requests.CreateChatRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateChatRequest
import ru.kotlix.frame.parties.server.mapper.toChatDto
import ru.kotlix.frame.parties.server.service.ChatService

@RestController
@RequestMapping("/api/v1")
class ChatController(
    private val chatService: ChatService,
) : ChatApi {
    @GetMapping("/community/{communityId}/chat")
    override fun getAllChats(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
    ): List<ChatDto> = chatService.getAllChats(initiatorId, communityId).map { it.toChatDto() }

    @GetMapping("/chat/{id}")
    override fun getChatById(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    ): ChatDto = chatService.getChatById(initiatorId, id).toChatDto()

    @PostMapping("/community/{communityId}/chat")
    override fun createChat(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
        @RequestBody
        request: CreateChatRequest,
    ): ChatDto =
        chatService.createChat(
            initiatorId,
            communityId,
            request.name,
            request.directoryId,
            request.order,
        ).toChatDto()

    @PutMapping("/chat/{id}")
    override fun updateChat(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
        @RequestBody
        request: UpdateChatRequest,
    ): ChatDto =
        chatService.updateChat(
            initiatorId,
            id,
            request.name,
            request.directoryId,
            request.order,
        ).toChatDto()

    @DeleteMapping("/chat/{id}")
    override fun deleteChat(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    ) = chatService.deleteChat(initiatorId, id)
}
