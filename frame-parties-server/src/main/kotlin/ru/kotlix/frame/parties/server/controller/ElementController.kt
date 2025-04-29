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

    @GetMapping
    fun getAll(
        @PathVariable communityId: Long
    ): ResponseEntity<List<Long>> =
        ResponseEntity.ok(elementService.getElementsByCommunityId(communityId))

    @GetMapping("/{elementId}")
    fun getById(
        @PathVariable communityId: Long,
        @PathVariable elementId: Long
    ): ResponseEntity<ElementDto> =
        ResponseEntity.ok(elementService.getElementById(communityId, elementId))

    @PostMapping("/roles")
    fun createDirectory(
        @PathVariable communityId: Long,
        @RequestBody request: CreateDirectoryElementRequest
    ): ResponseEntity<ElementDto> =
        ResponseEntity.ok(elementService.createDirectoryElement(communityId, request))

    @PutMapping("/roles/{elementId}")
    fun updateDirectory(
        @PathVariable communityId: Long,
        @PathVariable elementId: Long,
        @RequestBody request: UpdateDirectoryElementRequest
    ): ResponseEntity<ElementDto> =
        ResponseEntity.ok(elementService.updateDirectoryElement(communityId, elementId, request))

    @PostMapping("/voice-chats")
    fun createVoice(
        @PathVariable communityId: Long,
        @RequestBody request: CreateVoiceElementRequest
    ): ResponseEntity<ElementDto> =
        ResponseEntity.ok(elementService.createVoiceElement(communityId, request))

    @PutMapping("/voice-chats/{elementId}")
    fun updateVoice(
        @PathVariable communityId: Long,
        @PathVariable elementId: Long,
        @RequestBody request: UpdateVoiceElementRequest
    ): ResponseEntity<ElementDto> =
        ResponseEntity.ok(elementService.updateVoiceElement(communityId, elementId, request))

    @PostMapping("/text-chats")
    fun createText(
        @PathVariable communityId: Long,
        @RequestBody request: CreateTextElementRequest
    ): ResponseEntity<ElementDto> =
        ResponseEntity.ok(elementService.createTextElement(communityId, request))

    @PutMapping("/text-chats/{elementId}")
    fun updateText(
        @PathVariable communityId: Long,
        @PathVariable elementId: Long,
        @RequestBody request: UpdateTextElementRequest
    ): ResponseEntity<ElementDto> =
        ResponseEntity.ok(elementService.updateTextElement(communityId, elementId, request))


    @DeleteMapping("/{elementId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable communityId: Long,
        @PathVariable elementId: Long
    ) {
        elementService.deleteElement(communityId, elementId)
    }
}