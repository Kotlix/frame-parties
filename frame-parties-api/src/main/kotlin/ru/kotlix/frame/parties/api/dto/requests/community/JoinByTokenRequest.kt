package ru.kotlix.frame.parties.api.dto.requests.community

data class JoinByTokenRequest(
    val token: String,
    val userId: Long
)