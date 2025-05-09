package ru.kotlix.frame.parties.server.service

import ru.kotlix.frame.parties.server.repository.dto.DirectoryEntity

interface DirectoryService {
    fun getAllDirectories(
        initiatorId: Long,
        communityId: Long,
    ): List<DirectoryEntity>

    fun getDirectoryById(
        initiatorId: Long,
        directoryId: Long,
    ): DirectoryEntity

    fun createDirectory(
        initiatorId: Long,
        communityId: Long,
        name: String,
        parentDirectoryId: Long?,
        order: Int,
    ): DirectoryEntity

    fun updateDirectory(
        initiatorId: Long,
        id: Long,
        name: String,
        directoryId: Long?,
        order: Int,
    ): DirectoryEntity

    fun deleteDirectory(
        initiatorId: Long,
        id: Long,
    )
}
