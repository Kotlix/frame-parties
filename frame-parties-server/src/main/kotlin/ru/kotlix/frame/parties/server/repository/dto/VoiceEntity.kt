package ru.kotlix.frame.parties.server.repository.dto

import java.time.OffsetDateTime

data class VoiceEntity(
    var id: Long? = null,
    var createdAt: OffsetDateTime,
    var updatedAt: OffsetDateTime,
    var communityId: Long,
    var name: String,
    var parentDirectoryId: Long,
    var pos: Int,
)
