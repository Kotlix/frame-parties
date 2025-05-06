package ru.kotlix.frame.parties.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import ru.kotlix.frame.parties.api.DirectoryApi
import ru.kotlix.frame.parties.api.dto.entities.DirectoryDto
import ru.kotlix.frame.parties.api.dto.requests.CreateDirectoryRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateDirectoryRequest

@FeignClient(name = "frame-parties-directory-client", path = "/api/v1")
interface PartiesDirectoryClient : DirectoryApi {
    @GetMapping("/community/{communityId}/directory")
    override fun getAllDirectories(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
    ): List<DirectoryDto>

    @GetMapping("/directory/{id}")
    override fun getDirectoryById(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    ): DirectoryDto

    @PostMapping("/community/{communityId}/directory")
    override fun createDirectory(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
        @RequestBody
        request: CreateDirectoryRequest,
    ): DirectoryDto

    @PutMapping("/directory/{id}")
    override fun updateDirectory(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
        @RequestBody
        request: UpdateDirectoryRequest,
    ): DirectoryDto

    @DeleteMapping("/directory/{id}")
    override fun deleteDirectory(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    )
}
