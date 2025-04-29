package ru.kotlix.frame.parties.api.dto.requests.element

data class CreateDirectoryElementRequest(
    val name: String,
    val permissions: List<String>
)