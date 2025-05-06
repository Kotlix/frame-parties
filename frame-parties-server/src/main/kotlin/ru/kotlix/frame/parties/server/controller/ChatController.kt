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
import ru.kotlix.frame.parties.server.exception.todoex
import ru.kotlix.frame.parties.server.exception.todoex1

@RestController
@RequestMapping("/api/v1")
class ChatController() : ChatApi {
    @GetMapping("/community/{communityId}/chat")
    override fun getAllChats(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
    ): List<ChatDto> = todoex()

    @GetMapping("/chat/{id}")
    override fun getChatById(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    ): ChatDto = todoex()

    @PostMapping("/community/{communityId}/chat")
    override fun createChat(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
        @RequestBody
        request: CreateChatRequest,
    ): ChatDto = todoex()

    @PutMapping("/chat/{id}")
    override fun updateChat(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
        @RequestBody
        request: UpdateChatRequest,
    ): ChatDto = todoex()

    @DeleteMapping("/chat/{id}")
    override fun deleteChat(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    ) = todoex1()
}
