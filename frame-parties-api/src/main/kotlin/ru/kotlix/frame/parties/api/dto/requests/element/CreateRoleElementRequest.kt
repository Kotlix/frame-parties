package ru.kotlix.frame.parties.api.dto.requests.element

data class CreateRoleElementRequest(
    val name: String,
    val permissions: List<String>
)