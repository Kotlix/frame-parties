package ru.kotlix.frame.parties.api.service

import ru.kotlix.frame.parties.api.dto.entities.CommunicationDto


interface CommunicationService {
    fun sendMessage(elementId: Long, dto: CommunicationDto): CommunicationDto
    fun getMessagesByElementId(elementId: Long): List<CommunicationDto>
    fun getMessageById(elementId: Long, messageId: Long): CommunicationDto
    fun deleteMessage(elementId: Long, messageId: Long)
}