package ru.kotlix.frame.parties.api.service

import ru.kotlix.frame.parties.api.dto.entities.MessageDto
import ru.kotlix.frame.parties.api.dto.requests.communication.SendMessageRequest


interface MessageService {
    fun sendMessage(message: SendMessageRequest): MessageDto
    fun getMessagesByCommunityId(communityId: Long, chatId: Long, offset: Long, number: Long): List<MessageDto>
    fun getMessageById(communityId: Long, chatId: Long, messageId: Long): MessageDto
}