package ru.kotlix.frame.parties.server.repository

import ru.kotlix.frame.parties.server.repository.dto.VoiceEntity

interface VoiceEntityRepository {
    fun findById(id: Long): VoiceEntity?

    fun save(entity: VoiceEntity): VoiceEntity

    fun findAllByCommunityId(communityId: Long): List<VoiceEntity>

    fun update(entity: VoiceEntity): VoiceEntity
}
