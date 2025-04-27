package ru.kotlix.frame.parties.server.repository.dto

import java.time.OffsetDateTime

data class RoleEntity(
    val id: Long? = null,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
    val communityId: Long,
    val name: String,
    val priority: Int,
    val rights: Rights,
) {
    data class Rights(
        val serverDelete: Boolean,
        val serverEdit: Boolean,
        val serverEditRoles: Boolean,
        val serverEditElements: Boolean,
        val serverAssignRoles: Boolean,
        val chatSendMessages: Boolean,
        val voiceJoin: Boolean,
    )
}