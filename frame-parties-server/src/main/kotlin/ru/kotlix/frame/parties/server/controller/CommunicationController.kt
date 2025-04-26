package ru.kotlix.frame.parties.server.controller;

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*;
import ru.kotlix.frame.parties.api.dto.entities.CommunicationDto;
import ru.kotlix.frame.parties.api.service.CommunicationService;

@RestController
@RequestMapping("/api/elements/{elementId}/messages")
class CommunicationController(
    private val communicationService: CommunicationService
) {

    @PostMapping
    fun send(
        @PathVariable elementId: Long,
        @RequestBody dto: CommunicationDto
    ): ResponseEntity<CommunicationDto> {
        val created = communicationService.sendMessage(elementId, dto)
        return ResponseEntity.ok().body(created)
    }

    @GetMapping
    fun getAll(@PathVariable elementId: Long): ResponseEntity<List<CommunicationDto>> =
        ResponseEntity.ok(communicationService.getMessagesByElementId(elementId))

    @GetMapping("/{messageId}")
    fun getById(
        @PathVariable elementId: Long,
        @PathVariable messageId: Long
    ): ResponseEntity<CommunicationDto> =
        ResponseEntity.ok(communicationService.getMessageById(elementId, messageId))

    @DeleteMapping("/{messageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable elementId: Long,
        @PathVariable messageId: Long
    ) {
        communicationService.deleteMessage(elementId, messageId)
    }
}