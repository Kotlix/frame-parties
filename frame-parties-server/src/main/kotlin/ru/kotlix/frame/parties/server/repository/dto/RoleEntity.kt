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

fun RoleEntity.PermissionSet.toMap(): Map<String, Boolean> {
    return listOf(
        "serverDelete" to serverDelete,
        "serverEdit" to serverEdit,
        "serverEditRoles" to serverEditRoles,
        "serverEditElements" to serverEditElements,
        "serverAssignRoles" to serverAssignRoles,
        "serverCreateInvite" to serverCreateInvite,
        "chatSendMessages" to chatSendMessages,
        "voiceJoin" to voiceJoin,
    ).mapNotNull { (key, value) ->
        value?.let { key to it }
    }.toMap()
}
