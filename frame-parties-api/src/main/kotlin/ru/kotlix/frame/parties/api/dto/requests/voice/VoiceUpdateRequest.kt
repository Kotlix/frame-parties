package ru.kotlix.frame.parties.api.dto.requests.voice

import ru.kotlix.frame.parties.api.dto.ElementType

data class VoiceUpdateRequest(
    val community_id: Long,
    val name: String,
    val parent_element_id: Long?,
    val type: ElementType?,
    val order: Int?,
    val created_at: String?
)
