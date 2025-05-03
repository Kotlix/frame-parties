package ru.kotlix.frame.parties.server.service

import ru.kotlix.frame.parties.server.service.dto.CommunityPermission

interface RoleService {
    fun hasRight(
        membershipId: Long,
        communityRight: CommunityPermission,
    ): Boolean
}
