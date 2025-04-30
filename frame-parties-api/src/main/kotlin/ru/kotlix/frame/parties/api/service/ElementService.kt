package ru.kotlix.frame.parties.api.service

import ru.kotlix.frame.parties.api.dto.entities.ElementDto
import ru.kotlix.frame.parties.api.dto.requests.element.*


interface ElementService {
    fun createDirectoryElement(communityId: Long, request: CreateDirectoryElementRequest): ElementDto
    fun updateDirectoryElement(communityId: Long, elementId: Long, request: UpdateDirectoryElementRequest): ElementDto
    fun createTextElement(communityId: Long, request: CreateChatElementRequest): ElementDto
    fun updateTextElement(communityId: Long, elementId: Long, request: UpdateChatElementRequest): ElementDto
    fun getAllDirectoriesByCommunityId(communityId: Long): List<Long>?
    fun getDirectoryById(communityId: Long, elementId: Long): ElementDto?
    fun getChatById(communityId: Long, elementId: Long): ElementDto?
    fun getAllChatsByCommunityId(communityId: Long): List<Long>?
    fun deleteDirectory(communityId: Long, elementId: Long)
    fun deleteChat(communityId: Long, elementId: Long)
}
