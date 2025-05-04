package ru.kotlix.frame.parties.server.service

import org.springframework.stereotype.Service
import ru.kotlix.frame.parties.server.repository.RoleEntityRepository
import ru.kotlix.frame.parties.server.service.dto.CommunityPermission

@Service
class RoleServiceImpl(
    private val roleEntityRepository: RoleEntityRepository,
) : RoleService {
    override fun hasRight(
        membershipId: Long,
        communityRight: CommunityPermission,
    ): Boolean {
        val roleEntities = roleEntityRepository.findAllByMembershipId(membershipId)
        // Should be sorted by priority in desc order

        roleEntities.forEach { role ->
            when (communityRight) {
                CommunityPermission.SERVER_DELETE -> role.rights.serverDelete
                CommunityPermission.SERVER_EDIT -> role.rights.serverEdit
                CommunityPermission.SERVER_EDIT_ROLES -> role.rights.serverEditRoles
                CommunityPermission.SERVER_EDIT_ELEMENTS -> role.rights.serverEditElements
                CommunityPermission.SERVER_ASSIGN_ROLES -> role.rights.serverAssignRoles
                CommunityPermission.SERVER_CREATE_INVITE -> role.rights.serverCreateInvite
                CommunityPermission.CHAT_SEND_MESSAGES -> role.rights.chatSendMessages
                CommunityPermission.VOICE_JOIN -> role.rights.voiceJoin
            }?.let {
                return it
            }
        }

        return false
    }
}
