package ru.kotlix.frame.parties.server.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kotlix.frame.parties.api.dto.entities.ElementDto
import ru.kotlix.frame.parties.api.service.ElementService

@RestController
@RequestMapping("/api/communities/{communityId}/elements")
class ElementController(
    private val elementService: ElementService
) {

    @GetMapping
    fun getAll(
        @PathVariable communityId: Long
    ): ResponseEntity<List<ElementDto>> =
        ResponseEntity.ok(elementService.getElementsByCommunityId(communityId))

    @GetMapping("/{elementId}")
    fun getById(
        @PathVariable communityId: Long,
        @PathVariable elementId: Long
    ): ResponseEntity<ElementDto> =
        ResponseEntity.ok(elementService.getElementById(communityId, elementId))

    @PostMapping
    fun create(
        @PathVariable communityId: Long,
        @RequestBody dto: ElementDto
    ): ResponseEntity<ElementDto> {
        val created = elementService.createElement(communityId, dto)
        return ResponseEntity.ok().body(created)
    }

    @PutMapping("/{elementId}")
    fun update(
        @PathVariable communityId: Long,
        @PathVariable elementId: Long,
        @RequestBody dto: ElementDto
    ): ResponseEntity<ElementDto> =
        ResponseEntity.ok(
            elementService.updateElement(communityId, elementId, dto)
        )

    @DeleteMapping("/{elementId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable communityId: Long,
        @PathVariable elementId: Long
    ) {
        elementService.deleteElement(communityId, elementId)
    }
}