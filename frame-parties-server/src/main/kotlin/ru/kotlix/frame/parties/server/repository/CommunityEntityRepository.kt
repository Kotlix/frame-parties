package ru.kotlix.frame.parties.server.repository

import ru.kotlix.frame.parties.server.repository.dto.CommunityEntity

interface CommunityEntityRepository {
    fun findById(id: Long): CommunityEntity?

    fun save(entity: CommunityEntity): CommunityEntity

    fun findAllPublic(pageNumber: Int, pageSize: Int): List<CommunityEntity>

    fun findAllPublicByName(name: String, pageNumber: Int, pageSize: Int): List<CommunityEntity>
}