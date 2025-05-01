package ru.kotlix.frame.parties.api

import ru.kotlix.frame.parties.api.dto.entities.DirectoryDto
import ru.kotlix.frame.parties.api.dto.requests.CreateDirectoryRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateDirectoryRequest

interface DirectoryApi {
    fun getAllDirectories(communityId: Long): List<DirectoryDto>

    fun getDirectoryById(id: Long): DirectoryDto

    fun createDirectory(
        communityId: Long,
        request: CreateDirectoryRequest,
    ): DirectoryDto

    fun updateDirectory(
        id: Long,
        request: UpdateDirectoryRequest,
    ): DirectoryDto

    fun deleteDirectory(id: Long)
}
