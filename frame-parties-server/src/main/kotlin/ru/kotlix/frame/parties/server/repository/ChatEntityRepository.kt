package ru.kotlix.frame.parties.server.repository

import ru.kotlix.frame.parties.server.repository.dto.ChatEntity

interface ChatEntityRepository {
    fun findById(id: Long): ChatEntity?

    fun save(entity: ChatEntity): ChatEntity

    fun findAllByCommunityId(communityId: Long): List<ChatEntity>
}