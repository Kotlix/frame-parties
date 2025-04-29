package ru.kotlix.frame.parties.api.dto.requests.community

data class CreateCommunityRequest(
    val name: String,
    val description: String?
)

