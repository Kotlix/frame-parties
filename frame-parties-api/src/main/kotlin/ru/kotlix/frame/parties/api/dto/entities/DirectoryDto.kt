package ru.kotlix.frame.parties.api.dto.entities

class DirectoryDto(
    val id: Long,
    val communityId: Long,
    val name: String,
    val directoryId: Long?,
    val order: Int,
)
