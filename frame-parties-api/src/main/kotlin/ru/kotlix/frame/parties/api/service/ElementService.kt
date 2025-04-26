package ru.kotlix.frame.parties.api.service

import ru.kotlix.frame.parties.api.dto.entities.ElementDto


interface ElementService {
    fun getElementsByCommunityId(communityId: Long): List<ElementDto>
    fun getElementById(communityId: Long, elementId: Long): ElementDto
    fun createElement(communityId: Long, dto: ElementDto): ElementDto
    fun updateElement(communityId: Long, elementId: Long, dto: ElementDto): ElementDto
    fun deleteElement(communityId: Long, elementId: Long)
}
