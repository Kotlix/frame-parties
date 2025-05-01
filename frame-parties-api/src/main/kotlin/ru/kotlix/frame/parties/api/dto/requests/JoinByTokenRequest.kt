package ru.kotlix.frame.parties.api.dto.requests

data class JoinByTokenRequest(
    val token: String,
    val userId: Long,
)
