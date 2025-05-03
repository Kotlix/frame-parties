package ru.kotlix.frame.parties.server.repository

import ru.kotlix.frame.parties.server.repository.dto.DirectoryEntity

interface DirectoryEntityRepository {
    fun findById(id: Long): DirectoryEntity?

    fun save(entity: DirectoryEntity): DirectoryEntity

    fun findAllByCommunityId(communityId: Long): List<DirectoryEntity>
}
