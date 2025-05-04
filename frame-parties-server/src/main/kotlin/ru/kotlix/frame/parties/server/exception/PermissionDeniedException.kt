package ru.kotlix.frame.parties.server.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import ru.kotlix.frame.parties.server.service.dto.CommunityPermission

@ResponseStatus(HttpStatus.FORBIDDEN)
class PermissionDeniedException(message: String) : RuntimeException(message) {
    constructor(
        initiatorId: Long,
        communityPermission: CommunityPermission,
    ) : this("User id=$initiatorId has no $communityPermission permission.")
}
