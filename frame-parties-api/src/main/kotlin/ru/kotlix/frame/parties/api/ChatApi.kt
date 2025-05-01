package ru.kotlix.frame.parties.api

import ru.kotlix.frame.parties.api.dto.entities.ChatDto
import ru.kotlix.frame.parties.api.dto.requests.CreateChatRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateChatRequest

interface ChatApi {
    fun getAllChats(communityId: Long): List<ChatDto>

    fun getChatById(id: Long): ChatDto

    fun createChat(
        communityId: Long,
        request: CreateChatRequest,
    ): ChatDto

    fun updateChat(
        id: Long,
        request: UpdateChatRequest,
    ): ChatDto

    fun deleteChat(id: Long)
}
