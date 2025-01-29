package ru.kotlix.frame.parties.api.dto

data class CreateRoleRequest(
    val communityId: Int,
    val role: Role
) {
    data class Role(
        val name: String,
        val priority: Int,
        val rightsToChangeRights: Boolean,
        val changeableRights: List<String>,
        val inheritedRights: List<String>
    )
}