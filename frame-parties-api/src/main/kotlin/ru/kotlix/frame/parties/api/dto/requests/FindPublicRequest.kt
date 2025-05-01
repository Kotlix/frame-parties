package ru.kotlix.frame.parties.api.dto.requests

data class FindPublicRequest(
    val name: String,
    val pageOffset: Long,
    val pageCount: Long,
)
