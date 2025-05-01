package ru.kotlix.frame.parties.api.dto.requests

data class UpdateCommunityRequest(
    val name: String,
    val desc: String?,
    val isPublic: Boolean,
)
