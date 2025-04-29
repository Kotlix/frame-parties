package ru.kotlix.frame.parties.api.dto.requests.communication

import ru.kotlix.frame.parties.api.CommunityId
import java.time.LocalDateTime

data class CommunicationSendMessageRequest(
    val communityId: CommunityId,
    val elementId: Long,
    val senderId: Long?,
    val text: String?,
    val timestamp: LocalDateTime?
)