package ru.kotlix.frame.parties.api.dto.requests.element

data class UpdateRoleElementRequest(
    val name: String?,
    val permissions: List<String>?,
    val members: List<Long>?,
)