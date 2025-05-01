package ru.kotlix.frame.parties.api.dto.entities

class RoleDto(
    val id: Long,
    val communityId: Long,
    val roleName: String,
    val priority: Int,
    val rights: Map<String, Boolean>,
)
