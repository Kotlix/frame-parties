package ru.kotlix.frame.parties.api.dto

data class AssignRoleRequest(
    val communityId: String,
    val roleId: String,
    val assignerId: String,
    val assign: Boolean // true - assign, false - unassign
)
