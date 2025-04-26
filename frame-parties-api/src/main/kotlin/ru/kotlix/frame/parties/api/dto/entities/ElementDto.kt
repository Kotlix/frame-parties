package ru.kotlix.frame.parties.api.dto.entities

import java.time.LocalDateTime


class ElementDto(
    val id: Long?,
    val communityId: Long?,
    val parentElementId: Long?,
    val type: String?,
    val name: String?,
    val order: Int?,
    val createdAt: LocalDateTime?
)