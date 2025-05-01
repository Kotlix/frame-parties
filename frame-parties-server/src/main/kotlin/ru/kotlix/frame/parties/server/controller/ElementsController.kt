package ru.kotlix.frame.parties.server.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.kotlix.frame.parties.api.ChatApi
import ru.kotlix.frame.parties.api.DirectoryApi
import ru.kotlix.frame.parties.api.VoiceApi
import ru.kotlix.frame.parties.api.dto.entities.ChatDto
import ru.kotlix.frame.parties.api.dto.entities.ConnectionGuide
import ru.kotlix.frame.parties.api.dto.entities.DirectoryDto
import ru.kotlix.frame.parties.api.dto.entities.VoiceDto
import ru.kotlix.frame.parties.api.dto.requests.CreateChatRequest
import ru.kotlix.frame.parties.api.dto.requests.CreateDirectoryRequest
import ru.kotlix.frame.parties.api.dto.requests.CreateVoiceRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateChatRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateDirectoryRequest
import ru.kotlix.frame.parties.api.dto.requests.UpdateVoiceRequest

@RestController
@RequestMapping("/api/v1")
class ElementsController() : DirectoryApi, ChatApi, VoiceApi {
    @GetMapping("/community/{communityId}/directory")
    override fun getAllDirectories(
        @PathVariable("communityId")
        communityId: Long,
    ): List<DirectoryDto> = TODO()

    @GetMapping("/directory/{id}")
    override fun getDirectoryById(
        @PathVariable("id")
        id: Long,
    ): DirectoryDto = TODO()

    @PostMapping("/community/{communityId}/directory")
    override fun createDirectory(
        @PathVariable("communityId")
        communityId: Long,
        @RequestBody
        request: CreateDirectoryRequest,
    ): DirectoryDto = TODO()

    @PutMapping("/directory/{id}")
    override fun updateDirectory(
        @PathVariable("id")
        id: Long,
        @RequestBody
        request: UpdateDirectoryRequest,
    ): DirectoryDto = TODO()

    @DeleteMapping("/directory/{id}")
    override fun deleteDirectory(
        @PathVariable("id")
        id: Long,
    ) = TODO()

    @GetMapping("/community/{communityId}/chat")
    override fun getAllChats(
        @PathVariable("communityId")
        communityId: Long,
    ): List<ChatDto> = TODO()

    @GetMapping("/chat/{id}")
    override fun getChatById(
        @PathVariable("id")
        id: Long,
    ): ChatDto = TODO()

    @PostMapping("/community/{communityId}/chat")
    override fun createChat(
        @PathVariable("communityId")
        communityId: Long,
        @RequestBody
        request: CreateChatRequest,
    ): ChatDto = TODO()

    @PutMapping("/chat/{id}")
    override fun updateChat(
        @PathVariable("id")
        id: Long,
        @RequestBody
        request: UpdateChatRequest,
    ): ChatDto = TODO()

    @DeleteMapping("/chat/{id}")
    override fun deleteChat(
        @PathVariable("id")
        id: Long,
    ) = TODO()

    @GetMapping("/community/{communityId}/voice")
    override fun getAllVoices(
        @PathVariable("communityId")
        communityId: Long,
    ): List<VoiceDto> = TODO()

    @GetMapping("/voice/{id}")
    override fun getVoiceById(
        @PathVariable("id")
        id: Long,
    ): VoiceDto = TODO()

    @PostMapping("/community/{communityId}/voice")
    override fun createVoice(
        @PathVariable("communityId")
        communityId: Long,
        @RequestBody
        request: CreateVoiceRequest,
    ): VoiceDto = TODO()

    @PutMapping("/voice/{id}")
    override fun updateVoice(
        @PathVariable("id")
        id: Long,
        @RequestBody
        request: UpdateVoiceRequest,
    ): VoiceDto = TODO()

    @DeleteMapping("/voice/{id}")
    override fun deleteVoice(
        @PathVariable("id")
        id: Long,
    ) = TODO()

    @PostMapping("/voice-join")
    override fun joinVoice(
        @RequestParam
        id: Long,
        @RequestParam
        userId: Long,
    ): ConnectionGuide = TODO()

    @PostMapping("/voice-leave")
    override fun leaveVoice(
        @RequestParam
        id: Long,
        @RequestParam
        userId: Long,
    ) = TODO()
}
