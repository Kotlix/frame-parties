package ru.kotlix.frame.parties.api.service

import ru.kotlix.frame.parties.api.dto.entities.VoiceChat
import ru.kotlix.frame.parties.api.dto.requests.voice.VoiceCreateRequest
import ru.kotlix.frame.parties.api.dto.requests.voice.VoiceJoinRequest
import ru.kotlix.frame.parties.api.dto.requests.voice.VoiceUpdateRequest

interface VoiceService {
    fun createVoiceChat(communityId: Long, request: VoiceCreateRequest): String?
    fun joinVoiceChat(communityId: Long, request: VoiceJoinRequest): String?
    fun leaveVoiceChat(communityId: Long, userId: Long)
    fun getAll(communityId: Long): List<Long>?
    fun getVoiceChatById(communityId: Long, voiceId: Long): VoiceChat
    fun updateVoiceChat(communityId: Long, request: VoiceUpdateRequest): String?
}