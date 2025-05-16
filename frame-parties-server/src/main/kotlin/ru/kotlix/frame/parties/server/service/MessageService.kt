package ru.kotlix.frame.parties.server.service

import ru.kotlix.frame.parties.server.repository.dto.TextMessageEntity

interface MessageService {
    fun sendMessage(
        initiatorId: Long,
        chatId: Long,
        message: String,
    ): TextMessageEntity

    fun getMessages(
        initiatorId: Long,
        chatId: Long,
        page: Long,
        size: Long,
    ): List<TextMessageEntity>

    fun getMessageById(
        initiatorId: Long,
        messageId: Long,
    ): TextMessageEntity
}
