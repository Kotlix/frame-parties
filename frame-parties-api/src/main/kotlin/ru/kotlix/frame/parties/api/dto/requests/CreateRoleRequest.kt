package ru.kotlix.frame.parties.api.dto.requests

data class CreateRoleRequest(
    val roleName: String,
    val priority: Int,
    val rights: Map<String, Boolean>,
)
