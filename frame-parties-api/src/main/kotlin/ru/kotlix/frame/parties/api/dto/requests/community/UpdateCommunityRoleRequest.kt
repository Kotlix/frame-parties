package ru.kotlix.frame.parties.api.dto.requests.community

data class UpdateCommunityRoleRequest(
    val roleId: Long,
    val roleName: String
)