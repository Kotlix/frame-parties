package ru.kotlix.frame.parties.api.service

import ru.kotlix.frame.parties.api.dto.entities.ElementDto
import ru.kotlix.frame.parties.api.dto.requests.element.*


interface ElementService {
    // Common
    fun getElementsByCommunityId(communityId: Long): List<Long>
    fun getElementById(communityId: Long, elementId: Long): ElementDto
    fun deleteElement(communityId: Long, elementId: Long)

    fun createRoleElement(communityId: Long, request: CreateRoleElementRequest): ElementDto
    fun updateRoleElement(communityId: Long, elementId: Long, request: UpdateRoleElementRequest): ElementDto

    fun createVoiceElement(communityId: Long, request: CreateVoiceElementRequest): ElementDto
    fun updateVoiceElement(communityId: Long, elementId: Long, request: UpdateVoiceElementRequest): ElementDto

    fun createTextElement(communityId: Long, request: CreateTextElementRequest): ElementDto
    fun updateTextElement(communityId: Long, elementId: Long, request: UpdateTextElementRequest): ElementDto
}
