package ru.kotlix.frame.parties.api.dto.entities

data class Channel(
    val id: Int,
    val communityId: String,
    val elementIds: List<String>?
)