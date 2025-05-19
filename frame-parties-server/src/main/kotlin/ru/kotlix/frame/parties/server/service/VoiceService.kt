package ru.kotlix.frame.parties.server.service

import ru.kotlix.frame.parties.api.dto.entities.ConnectionGuide
import ru.kotlix.frame.parties.server.repository.dto.VoiceEntity

interface VoiceService {
    fun getAllVoices(
        initiatorId: Long,
        communityId: Long,
    ): List<VoiceEntity>

    fun getVoiceById(
        initiatorId: Long,
        id: Long,
    ): VoiceEntity

    fun createVoice(
        initiatorId: Long,
        communityId: Long,
        name: String,
        directoryId: Long,
        order: Int,
    ): VoiceEntity

    fun updateVoice(
        initiatorId: Long,
        id: Long,
        name: String,
        directoryId: Long,
        order: Int,
    ): VoiceEntity

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

    fun createVoice(
        communityId: Long,
        name: String,
        directoryId: Long,
        order: Int,
    ): VoiceEntity

    fun getVoiceUsers(
        initiatorId: Long,
        id: Long,
    ): List<Long>
}
