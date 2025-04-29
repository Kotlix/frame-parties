package ru.kotlix.frame.parties.api.dto.requests.element

data class UpdateDirectoryElementRequest(
    val name: String?,
    val permissions: List<String>?,
    val members: List<Long>?,
)