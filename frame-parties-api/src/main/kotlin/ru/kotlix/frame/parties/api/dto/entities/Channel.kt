package ru.kotlix.frame.parties.api.dto.entities

data class Channel(
    val id: String,
    val communityId: String,
    val elementIds: List<String>?
)