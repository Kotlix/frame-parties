package ru.kotlix.frame.parties.api.dto.requests

data class CreateDirectoryRequest(
    val name: String,
    val directoryId: Long?,
    val order: Int,
)
