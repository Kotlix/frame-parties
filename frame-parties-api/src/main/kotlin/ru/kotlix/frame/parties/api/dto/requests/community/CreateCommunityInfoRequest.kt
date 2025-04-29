package ru.kotlix.frame.parties.api.dto.requests.community

data class CreateCommunityInfoRequest(
    val name: String,
    val description: String?
)