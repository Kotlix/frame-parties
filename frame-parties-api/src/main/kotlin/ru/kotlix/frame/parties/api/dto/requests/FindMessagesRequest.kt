package ru.kotlix.frame.parties.api.dto.requests

data class FindMessagesRequest(
    val name: String,
    val pageOffset: Long,
    val pageCount: Long,
)
