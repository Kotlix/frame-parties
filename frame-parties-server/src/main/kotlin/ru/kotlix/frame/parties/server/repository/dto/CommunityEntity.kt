package ru.kotlix.frame.parties.server.repository.dto

import java.time.OffsetDateTime

data class CommunityEntity(
    var id: Long? = null,
    var createdAt: OffsetDateTime,
    var updatedAt: OffsetDateTime,
    var name: String,
    var isPublic: Boolean,
    var description: String?,
    var voiceName: String,
    var voiceRegion: String,
    var creatorId: Long,
    var deleted: Boolean,
)
