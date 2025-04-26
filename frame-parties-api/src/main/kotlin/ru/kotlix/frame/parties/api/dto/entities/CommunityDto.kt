package ru.kotlix.frame.parties.api.dto.entities

import java.time.LocalDateTime


class CommunityDto(
    val id: Long?,
    val name: String?,
    val description: String?,
    val isPublic: Boolean?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)
