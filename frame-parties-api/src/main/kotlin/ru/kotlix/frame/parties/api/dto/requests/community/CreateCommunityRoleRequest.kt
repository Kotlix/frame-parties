package ru.kotlix.frame.parties.api.dto.requests.community

data class CreateCommunityRoleRequest(
    val communityId: Long,
    val roleName: String,
    val permissions: List<String>
)