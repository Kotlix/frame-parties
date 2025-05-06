package ru.kotlix.frame.parties.server.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.kotlix.frame.parties.api.DirectoryApi
import ru.kotlix.frame.parties.api.dto.entities.DirectoryDto
import ru.kotlix.frame.parties.api.dto.requests.CreateDirectoryRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateDirectoryRequest
import ru.kotlix.frame.parties.server.exception.todoex
import ru.kotlix.frame.parties.server.exception.todoex1

@RestController
@RequestMapping("/api/v1")
class DirectoryController() : DirectoryApi {
    @GetMapping("/community/{communityId}/directory")
    override fun getAllDirectories(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
    ): List<DirectoryDto> = todoex()

    @GetMapping("/directory/{id}")
    override fun getDirectoryById(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    ): DirectoryDto = todoex()

    @PostMapping("/community/{communityId}/directory")
    override fun createDirectory(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("communityId")
        communityId: Long,
        @RequestBody
        request: CreateDirectoryRequest,
    ): DirectoryDto = todoex()

    @PutMapping("/directory/{id}")
    override fun updateDirectory(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
        @RequestBody
        request: UpdateDirectoryRequest,
    ): DirectoryDto = todoex()

    @DeleteMapping("/directory/{id}")
    override fun deleteDirectory(
        @RequestHeader("Initiator-Id")
        initiatorId: Long,
        @PathVariable("id")
        id: Long,
    ) = todoex1()
}
