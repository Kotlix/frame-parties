package ru.kotlix.frame.parties.api.dto

data class EditRoleRequest(
    val id: Int,
    val communityId: Int
) {
    data class Role(
        val name: String,
        val priority: Int,
        val rightsToChangeRights: String,
        val changeableRights: List<String>,
        val inheritedRights: List<String>
    )
}
