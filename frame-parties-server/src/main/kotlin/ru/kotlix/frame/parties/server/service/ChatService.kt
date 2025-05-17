package ru.kotlix.frame.parties.server.service

import ru.kotlix.frame.parties.server.repository.dto.ChatEntity

interface ChatService {
    fun getAllChats(
        initiatorId: Long,
        communityId: Long,
    ): List<ChatEntity>

    fun getChatById(
        initiatorId: Long,
        id: Long,
    ): ChatEntity

    fun createChat(
        initiatorId: Long,
        communityId: Long,
        name: String,
        directoryId: Long,
        order: Int,
    ): ChatEntity

    fun updateChat(
        initiatorId: Long,
        id: Long,
        name: String,
        directoryId: Long,
        order: Int,
    ): ChatEntity

    fun deleteChat(
        initiatorId: Long,
        id: Long,
    )

    fun createChat(
        communityId: Long,
        name: String,
        directoryId: Long,
        order: Int,
    ): ChatEntity
}
