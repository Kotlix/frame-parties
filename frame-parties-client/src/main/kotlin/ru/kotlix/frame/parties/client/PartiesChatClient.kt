package ru.kotlix.frame.parties.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import ru.kotlix.frame.parties.api.ChatApi
import ru.kotlix.frame.parties.api.dto.entities.ChatDto
import ru.kotlix.frame.parties.api.dto.requests.CreateChatRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateChatRequest

@FeignClient(name = "frame-parties-chat-client", path = "/api/v1")
interface PartiesChatClient : ChatApi {
    @GetMapping("/community/{communityId}/chat")
    override fun getAllChats(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
    ): List<ChatDto>

    @GetMapping("/chat/{id}")
    override fun getChatById(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    ): ChatDto

    @PostMapping("/community/{communityId}/chat")
    override fun createChat(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
        @RequestBody
        request: CreateChatRequest,
    ): ChatDto

    @PutMapping("/chat/{id}")
    override fun updateChat(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
        @RequestBody
        request: UpdateChatRequest,
    ): ChatDto

    @DeleteMapping("/chat/{id}")
    override fun deleteChat(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    )
}
