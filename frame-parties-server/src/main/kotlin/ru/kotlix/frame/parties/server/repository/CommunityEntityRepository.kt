package ru.kotlix.frame.parties.server.repository

import ru.kotlix.frame.parties.server.repository.dto.CommunityEntity

interface CommunityEntityRepository {
    fun findById(id: Long): CommunityEntity?

    fun save(entity: CommunityEntity): CommunityEntity

    fun update(entity: CommunityEntity): CommunityEntity

    fun findAllPublic(
        pageNumber: Long,
        pageSize: Long,
    ): List<CommunityEntity>

    fun findAllPublicByName(
        name: String,
        pageNumber: Long,
        pageSize: Long,
    ): List<CommunityEntity>

    fun findAllByCreatorId(creatorId: Long): List<CommunityEntity>

    fun findAllByUserId(userId: Long): List<CommunityEntity>
}
