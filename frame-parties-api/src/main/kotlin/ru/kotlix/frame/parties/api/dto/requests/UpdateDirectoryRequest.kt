package ru.kotlix.frame.parties.api.dto.requests

data class UpdateDirectoryRequest(
    val name: String,
    val directoryId: Long?,
    val order: Int,
)
