package ru.kotlix.frame.parties.server.controller

import org.springframework.web.bind.annotation.*
import ru.kotlix.frame.parties.api.DirectoryApi
import ru.kotlix.frame.parties.api.dto.entities.DirectoryDto
import ru.kotlix.frame.parties.api.dto.requests.CreateDirectoryRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateDirectoryRequest
import ru.kotlix.frame.parties.server.mapper.toDirectoryDto
import ru.kotlix.frame.parties.server.service.DirectoryService

@RestController
@RequestMapping("/api/v1")
class DirectoryController(
    val directoryService: DirectoryService
) : DirectoryApi {
    @GetMapping("/community/{communityId}/directory")
    override fun getAllDirectories(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long
    ): List<DirectoryDto> = directoryService.getAllDirectories(initiatorId, communityId).map { it.toDirectoryDto() }

    @GetMapping("/directory/{id}")
    override fun getDirectoryById(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long
    ): DirectoryDto = directoryService.getDirectoryById(initiatorId, id).toDirectoryDto()

    @PostMapping("/community/{communityId}/directory")
    override fun createDirectory(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
        @RequestBody
        request: CreateDirectoryRequest
    ): DirectoryDto = directoryService.createDirectory(
        initiatorId,
        communityId,
        request.name,
        request.directoryId,
        request.order
    ).toDirectoryDto()

    @PutMapping("/directory/{id}")
    override fun updateDirectory(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
        @RequestBody
        request: UpdateDirectoryRequest
    ): DirectoryDto = directoryService.updateDirectory(
        initiatorId,
        id,
        request.name,
        request.directoryId,
        request.order
    ).toDirectoryDto()

    @DeleteMapping("/directory/{id}")
    override fun deleteDirectory(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long
    ) = directoryService.deleteDirectory(initiatorId, id)
}
