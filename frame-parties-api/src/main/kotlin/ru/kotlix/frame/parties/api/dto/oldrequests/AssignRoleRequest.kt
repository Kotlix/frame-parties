package ru.kotlix.frame.parties.api.dto.oldrequests

data class AssignRoleRequest(
    val communityId: Int,
    val roleId: Int,
    val assignerId: Int,
    val assign: Boolean // true - assign, false - unassign
)
