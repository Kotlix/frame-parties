package ru.kotlix.frame.parties.api

import ru.kotlix.frame.parties.api.dto.entities.DirectoryDto
import ru.kotlix.frame.parties.api.dto.requests.CreateDirectoryRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateDirectoryRequest

interface DirectoryApi {
    fun getAllDirectories(
        initiatorId: Long,
        communityId: Long,
    ): List<DirectoryDto>

    fun getDirectoryById(
        initiatorId: Long,
        id: Long,
    ): DirectoryDto

    fun createDirectory(
        initiatorId: Long,
        communityId: Long,
        request: CreateDirectoryRequest,
    ): DirectoryDto

    fun updateDirectory(
        initiatorId: Long,
        id: Long,
        request: UpdateDirectoryRequest,
    ): DirectoryDto

    fun deleteDirectory(
        initiatorId: Long,
        id: Long,
    )
}
