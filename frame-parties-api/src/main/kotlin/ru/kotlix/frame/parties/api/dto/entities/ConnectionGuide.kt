package ru.kotlix.frame.parties.api.dto.entities

data class ConnectionGuide(
    val hostAddress: String,
    val secret: String,
    val channelId: Long,
    val shadowId: Int,
)
