package ru.kotlix.frame.parties.api.service

import ru.kotlix.frame.parties.api.dto.entities.CommunicationResponse
import ru.kotlix.frame.parties.api.dto.requests.communication.CommunicationSendMessageRequest
import ru.kotlix.frame.parties.api.dto.requests.communication.VoiceJoinRequest


interface CommunicationService {
    fun sendMessage(message: CommunicationSendMessageRequest): CommunicationResponse
    fun getMessagesByCommunityId(communityId: Long, offset: Long, number: Long): List<CommunicationResponse>
    fun getMessageById(communityId: Long, elementId: Long, messageId: Long): CommunicationResponse
    fun joinVoiceChannel(communityId: Long, request: VoiceJoinRequest): String
    fun leaveVoiceChannel(communityId: Long, userId: Long)
}