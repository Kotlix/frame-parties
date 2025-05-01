package ru.kotlix.frame.parties.api

import ru.kotlix.frame.parties.api.dto.entities.MessageDto
import ru.kotlix.frame.parties.api.dto.requests.FindMessagesRequest
import ru.kotlix.frame.parties.api.dto.requests.SendMessageRequest

interface MessageApi {
    fun sendMessage(
        chatId: Long,
        request: SendMessageRequest,
    ): MessageDto

    fun getMessages(
        chatId: Long,
        request: FindMessagesRequest,
    ): List<MessageDto>

    fun getById(messageId: Long): MessageDto
}
