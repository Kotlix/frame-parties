package ru.kotlix.frame.parties.api.dto.requests

data class CreateCommunityRequest(
    val name: String,
    val desc: String?,
    val isPublic: Boolean,
)
