package ru.kotlix.frame.parties.api

import ru.kotlix.frame.parties.api.dto.entities.MessageDto
import ru.kotlix.frame.parties.api.dto.requests.SendMessageRequest

interface MessageApi {
    fun sendMessage(
        initiatorId: Long,
        chatId: Long,
        request: SendMessageRequest,
    ): MessageDto

    fun getMessages(
        initiatorId: Long,
        chatId: Long,
        page: Long,
        size: Long,
    ): List<MessageDto>

    fun getById(
        initiatorId: Long,
        messageId: Long,
    ): MessageDto
}
