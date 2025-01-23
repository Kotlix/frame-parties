package ru.kotlix.frame.parties.api.dto

data class EditRoleRequest(
    val id: String,
    val communityId: String
) {
    data class Role(
        val name: String,
        val priority: String,
        val rightsToChangeRights: String,
        val changeableRights: List<String>,
        val inheritedRights: List<String>
    )
}
