package ru.kotlix.frame.parties.server.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kotlix.frame.parties.api.dto.entities.ElementDto
import ru.kotlix.frame.parties.api.dto.requests.element.*
import ru.kotlix.frame.parties.api.service.ElementService

@RestController
@RequestMapping("/api/communities/{communityId}/elements")
class ElementController(
    private val elementService: ElementService
) {

    @GetMapping("/directory")
    fun getAllDirectories(
        @PathVariable communityId: Long
    ): ResponseEntity<List<Long>> =
        ResponseEntity.ok(elementService.getAllDirectoriesByCommunityId(communityId))

    @GetMapping("/text-chats")
    fun getAllChats(
        @PathVariable communityId: Long
    ): ResponseEntity<List<Long>> =
        ResponseEntity.ok(elementService.getAllChatsByCommunityId(communityId))

    @GetMapping("/directory/{elementId}")
    fun getDirectoryById(
        @PathVariable communityId: Long,
        @PathVariable elementId: Long
    ): ResponseEntity<ElementDto> =
        ResponseEntity.ok(elementService.getDirectoryById(communityId, elementId))

    @GetMapping("/text-chats/{elementId}")
    fun getChatById(
        @PathVariable communityId: Long,
        @PathVariable elementId: Long
    ): ResponseEntity<ElementDto> =
        ResponseEntity.ok(elementService.getChatById(communityId, elementId))

    @PostMapping("/directory")
    fun createDirectory(
        @PathVariable communityId: Long,
        @RequestBody request: CreateDirectoryElementRequest
    ): ResponseEntity<ElementDto> =
        ResponseEntity.ok(elementService.createDirectoryElement(communityId, request))

    @PutMapping("/directory/{elementId}")
    fun updateDirectory(
        @PathVariable communityId: Long,
        @PathVariable elementId: Long,
        @RequestBody request: UpdateDirectoryElementRequest
    ): ResponseEntity<ElementDto> =
        ResponseEntity.ok(elementService.updateDirectoryElement(communityId, elementId, request))

    @PostMapping("/text-chats")
    fun createText(
        @PathVariable communityId: Long,
        @RequestBody request: CreateChatElementRequest
    ): ResponseEntity<ElementDto> =
        ResponseEntity.ok(elementService.createTextElement(communityId, request))

    @PutMapping("/text-chats/{elementId}")
    fun updateText(
        @PathVariable communityId: Long,
        @PathVariable elementId: Long,
        @RequestBody request: UpdateChatElementRequest
    ): ResponseEntity<ElementDto> =
        ResponseEntity.ok(elementService.updateTextElement(communityId, elementId, request))


    @DeleteMapping("/directory/{elementId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteDirectory(
        @PathVariable communityId: Long,
        @PathVariable elementId: Long
    ) {
        elementService.deleteDirectory(communityId, elementId)
    }

    @DeleteMapping("/text-chats/{elementId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteChat(
        @PathVariable communityId: Long,
        @PathVariable elementId: Long
    ) {
        elementService.deleteChat(communityId, elementId)
    }
}