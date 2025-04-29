package ru.kotlix.frame.parties.api.dto.requests.community

data class CreateCommunityChatRequest(
    val communityId: Long,
    val chatTitle: String,
    val chatSettings: String
)