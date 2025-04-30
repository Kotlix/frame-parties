package ru.kotlix.frame.parties.api.dto.requests.element

import ru.kotlix.frame.parties.api.dto.ElementType

data class CreateChatElementRequest(
    val community_id: Long,
    val name: String,
    val parent_element_id: Long?,
    val type: ElementType?,
    val order: Int,
    val created_at: String?
)