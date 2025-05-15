package ru.kotlix.frame.parties.server.repository.dto

import java.time.OffsetDateTime

data class RoleEntity(
    var id: Long? = null,
    var createdAt: OffsetDateTime,
    var updatedAt: OffsetDateTime,
    var communityId: Long,
    var name: String,
    var priority: Int,
    var protected: Boolean,
    var permissionSet: PermissionSet,
) {
    data class PermissionSet(
        val serverDelete: Boolean?,
        val serverEdit: Boolean?,
        val serverEditRoles: Boolean?,
        val serverEditElements: Boolean?,
        val serverAssignRoles: Boolean?,
        val serverCreateInvite: Boolean?,
        val chatSendMessages: Boolean?,
        val voiceJoin: Boolean?,
    )
}
