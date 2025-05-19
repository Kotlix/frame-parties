package ru.kotlix.frame.parties.api

import ru.kotlix.frame.parties.api.dto.entities.ConnectionGuide
import ru.kotlix.frame.parties.api.dto.entities.VoiceDto
import ru.kotlix.frame.parties.api.dto.requests.CreateVoiceRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateVoiceRequest

interface VoiceApi {
    fun getAllVoices(
        initiatorId: Long,
        communityId: Long,
    ): List<VoiceDto>

    fun getVoiceById(
        initiatorId: Long,
        id: Long,
    ): VoiceDto

    fun createVoice(
        initiatorId: Long,
        communityId: Long,
        request: CreateVoiceRequest,
    ): VoiceDto

    fun updateVoice(
        initiatorId: Long,
        id: Long,
        request: UpdateVoiceRequest,
    ): VoiceDto

    fun deleteVoice(
        initiatorId: Long,
        id: Long,
    )

    fun joinVoice(
        initiatorId: Long,
        id: Long,
    ): ConnectionGuide

    fun leaveVoice(
        initiatorId: Long,
        id: Long,
    )

    fun getVoiceUsers(
        initiatorId: Long,
        id: Long,
    ): List<Long>
}
