package ru.kotlix.frame.parties.api

import ru.kotlix.frame.parties.api.dto.entities.ChatDto
import ru.kotlix.frame.parties.api.dto.requests.CreateChatRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateChatRequest

interface ChatApi {
    fun getAllChats(
        initiatorId: Long,
        communityId: Long,
    ): List<ChatDto>

    fun getChatById(
        initiatorId: Long,
        id: Long,
    ): ChatDto

    fun createChat(
        initiatorId: Long,
        communityId: Long,
        request: CreateChatRequest,
    ): ChatDto

    fun updateChat(
        initiatorId: Long,
        id: Long,
        request: UpdateChatRequest,
    ): ChatDto

    fun deleteChat(
        initiatorId: Long,
        id: Long,
    )
}
