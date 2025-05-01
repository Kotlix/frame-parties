package ru.kotlix.frame.parties.api

import ru.kotlix.frame.parties.api.dto.entities.ConnectionGuide
import ru.kotlix.frame.parties.api.dto.entities.VoiceDto
import ru.kotlix.frame.parties.api.dto.requests.CreateVoiceRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateVoiceRequest

interface VoiceApi {
    fun getAllVoices(communityId: Long): List<VoiceDto>

    fun getVoiceById(id: Long): VoiceDto

    fun createVoice(
        communityId: Long,
        request: CreateVoiceRequest,
    ): VoiceDto

    fun updateVoice(
        id: Long,
        request: UpdateVoiceRequest,
    ): VoiceDto

    fun deleteVoice(id: Long)

    fun joinVoice(
        id: Long,
        userId: Long,
    ): ConnectionGuide

    fun leaveVoice(
        id: Long,
        userId: Long,
    )
}
