package ru.kotlix.frame.parties.server.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.kotlix.frame.parties.server.exception.NotFoundException
import ru.kotlix.frame.parties.server.exception.OperationDeniedException
import ru.kotlix.frame.parties.server.exception.PermissionDeniedException
import ru.kotlix.frame.parties.server.repository.CommunityEntityRepository
import ru.kotlix.frame.parties.server.repository.MembershipEntityRepository
import ru.kotlix.frame.parties.server.repository.MembershipRoleEntityRepository
import ru.kotlix.frame.parties.server.repository.RoleEntityRepository
import ru.kotlix.frame.parties.server.repository.dto.MembershipEntity
import ru.kotlix.frame.parties.server.repository.dto.MembershipRoleEntity
import ru.kotlix.frame.parties.server.repository.dto.RoleEntity
import ru.kotlix.frame.parties.server.service.dto.CommunityPermission
import java.time.OffsetDateTime

@Service
class RoleServiceImpl(
    private val roleEntityRepository: RoleEntityRepository,
    private val communityEntityRepository: CommunityEntityRepository,
    private val membershipEntityRepository: MembershipEntityRepository,
    private val membershipRoleEntityRepository: MembershipRoleEntityRepository,
) : RoleService {
    private final val defaultUserRolePriority = 0
    private val defaultUserRoleName = "default"
    private val defaultUserRolePermissionSet =
        RoleEntity.PermissionSet(
            serverDelete = false,
            serverEdit = false,
            serverEditRoles = false,
            serverEditElements = false,
            serverAssignRoles = false,
            serverCreateInvite = false,
            chatSendMessages = true,
            voiceJoin = true,
        )
    private final val defaultAdminRolePriority = defaultUserRolePriority + 1000
    private val defaultAdminRoleName = "admin"
    private val defaultAdminRolePermissionSet =
        RoleEntity.PermissionSet(
            serverDelete = true,
            serverEdit = true,
            serverEditRoles = true,
            serverEditElements = true,
            serverAssignRoles = true,
            serverCreateInvite = true,
            chatSendMessages = true,
            voiceJoin = true
        )
    private val blockPrioritiesOutOfDefaults = true

    private val communityRolesLimit = 100
    private val membershipRolesLimit = 100
    private val blockLowerPrioritiesAccessingHigherPriorities = true

    private val roleCreatePermission = CommunityPermission.SERVER_EDIT_ROLES
    private val roleUpdatePermission = CommunityPermission.SERVER_EDIT_ROLES
    private val roleDeletePermission = CommunityPermission.SERVER_EDIT_ROLES
    private val roleAssignPermission = CommunityPermission.SERVER_EDIT_ROLES
    private val roleUnassignPermission = CommunityPermission.SERVER_EDIT_ROLES

    private fun validateRolePriorityAccessibility(assigningPriority: Int) =
        if (blockPrioritiesOutOfDefaults) {
            defaultUserRolePriority < assigningPriority && assigningPriority < defaultAdminRolePriority
        } else {
            true
        }

    private fun validateRoleCreationAccessibility(communityId: Long) =
        roleEntityRepository.findAllByCommunityId(communityId).count() < communityRolesLimit

    private fun validateRoleAssignAccessibility(membershipId: Long) =
        roleEntityRepository.findAllByMembershipId(membershipId).count() < membershipRolesLimit

    private fun validatePriorityAccess(
        ownPriority: Int,
        assigningPriority: Int,
    ) = if (blockLowerPrioritiesAccessingHigherPriorities) {
        assigningPriority <= ownPriority
    } else {
        true
    }

    @Transactional
    override fun getAllRoles(
        initiatorId: Long,
        communityId: Long,
    ): List<RoleEntity> {
        val community =
            communityEntityRepository.findById(communityId)
                ?: throw NotFoundException.CommunityById(communityId)
        val isMember =
            membershipEntityRepository
                .findAllByUserId(initiatorId)
                .any { it.communityId == communityId }
        if (!isMember) {
            if (community.isPublic) {
                throw OperationDeniedException("Only members can see community roles.")
            } else {
                throw NotFoundException.CommunityById(communityId)
            }
        }

        return roleEntityRepository.findAllByCommunityId(community.id!!)
    }

    @Transactional
    override fun getRole(
        initiatorId: Long,
        id: Long,
    ): RoleEntity {
        val role =
            roleEntityRepository.findById(id)
                ?: throw NotFoundException.RoleById(id)
        val communityId = role.communityId

        val community =
            communityEntityRepository.findById(communityId)
                ?: throw RuntimeException("Role id=$id exists but its related community id=$communityId does not.")
        val isMember =
            membershipEntityRepository.findAllByUserId(initiatorId)
                .any { it.communityId == communityId }
        if (!isMember) {
            if (community.isPublic) {
                throw OperationDeniedException("Only members can see community roles.")
            } else {
                throw NotFoundException.CommunityById(communityId)
            }
        }

        return role
    }

    @Transactional
    override fun createRole(
        initiatorId: Long,
        communityId: Long,
        roleName: String,
        priority: Int,
        rights: Map<String, Boolean>,
    ): RoleEntity {
        if (!validateRolePriorityAccessibility(priority)) {
            throw OperationDeniedException("Inaccessible priority.")
        }

        val community =
            communityEntityRepository.findById(communityId)
                ?: throw NotFoundException.CommunityById(communityId)

        val membership =
            membershipEntityRepository.findAllByUserId(initiatorId)
                .find { it.communityId == communityId }
                ?: if (community.isPublic) {
                    throw OperationDeniedException("Only members can see roles.")
                } else {
                    throw NotFoundException.CommunityById(communityId)
                }

        if (!hasRight(membership.id!!, roleCreatePermission)) {
            throw PermissionDeniedException(initiatorId, roleCreatePermission)
        }
        if (!validateRoleCreationAccessibility(communityId)) {
            throw OperationDeniedException("Roles limit reached.")
        }
        val ownPriority = getPriority(membership.id!!)
        if (!validatePriorityAccess(ownPriority, priority)) {
            throw OperationDeniedException("Unable to access priority.")
        }

        val permissionSet = mapToPermissionSet(rights)
        return createRole(communityId, roleName, priority, false, permissionSet)
    }

    @Transactional
    override fun updateRole(
        initiatorId: Long,
        id: Long,
        roleName: String,
        priority: Int,
        rights: Map<String, Boolean>,
    ): RoleEntity {
        if (!validateRolePriorityAccessibility(priority)) {
            throw OperationDeniedException("Inaccessible priority.")
        }

        val role =
            roleEntityRepository.findById(id)
                ?: throw NotFoundException.RoleById(id)
        val communityId = role.communityId

        val community =
            communityEntityRepository.findById(communityId)
                ?: throw RuntimeException("Role id=$id exists but its related community id=$communityId does not.")

        val membership =
            membershipEntityRepository.findAllByUserId(initiatorId)
                .find { it.communityId == communityId }
                ?: if (community.isPublic) {
                    throw OperationDeniedException("Only members can update roles.")
                } else {
                    throw NotFoundException.CommunityById(communityId)
                }

        if (!hasRight(membership.id!!, roleUpdatePermission)) {
            throw PermissionDeniedException(initiatorId, roleUpdatePermission)
        }
        val ownPriority = getPriority(membership.id!!)
        if (!validatePriorityAccess(ownPriority, priority)) {
            throw OperationDeniedException("Unable to access priority.")
        }

        role.name = roleName
        role.priority = priority
        role.permissionSet = mapToPermissionSet(rights)
        // TODO: notify users about role update
        return roleEntityRepository.update(role)
    }

    @Transactional
    override fun deleteRole(
        initiatorId: Long,
        id: Long,
    ) {
        val role =
            roleEntityRepository.findById(id)
                ?: throw NotFoundException.RoleById(id)
        val communityId = role.communityId

        val community =
            communityEntityRepository.findById(communityId)
                ?: throw RuntimeException("Role id=$id exists but its related community id=$communityId does not.")

        val membership =
            membershipEntityRepository.findAllByUserId(initiatorId)
                .find { it.communityId == communityId }
                ?: if (community.isPublic) {
                    throw OperationDeniedException("Only members can delete roles.")
                } else {
                    throw NotFoundException.CommunityById(communityId)
                }

        if (!hasRight(membership.id!!, roleDeletePermission)) {
            throw PermissionDeniedException(initiatorId, roleDeletePermission)
        }
        val ownPriority = getPriority(membership.id!!)
        if (!validatePriorityAccess(ownPriority, role.priority)) {
            throw OperationDeniedException("Unable to access priority.")
        }

        deleteRole(role)
        // TODO: notify users about role deletion
    }

    @Transactional
    override fun assignRole(
        initiatorId: Long,
        targetId: Long,
        id: Long,
    ) {
        val role =
            roleEntityRepository.findById(id)
                ?: throw NotFoundException.RoleById(id)
        val communityId = role.communityId

        val community =
            communityEntityRepository.findById(communityId)
                ?: throw RuntimeException("Role id=$id exists but its related community id=$communityId does not.")

        val membership =
            membershipEntityRepository.findAllByUserId(initiatorId)
                .find { it.communityId == communityId }
                ?: if (community.isPublic) {
                    throw OperationDeniedException("Only members can assign roles.")
                } else {
                    throw NotFoundException.CommunityById(communityId)
                }

        if (!hasRight(membership.id!!, roleAssignPermission)) {
            throw PermissionDeniedException(initiatorId, roleAssignPermission)
        }

        val targetMembership =
            membershipEntityRepository.findAllByUserId(targetId)
                .find { it.communityId == communityId }
                ?: if (community.isPublic) {
                    throw OperationDeniedException("Roles can be assigned only to members.")
                } else {
                    throw NotFoundException.CommunityById(communityId)
                }

        roleEntityRepository.findAllByMembershipId(targetMembership.id!!)
            .find { it.id == role.id!! }
            ?.let { throw OperationDeniedException("Already assigned.") }

        if (!validateRoleAssignAccessibility(targetMembership.id!!)) {
            throw OperationDeniedException("Roles limit reached.")
        }
        val ownPriority = getPriority(membership.id!!)
        if (!validatePriorityAccess(ownPriority, role.priority)) {
            throw OperationDeniedException("Unable to access priority.")
        }

        assignRole(targetMembership, role)
        // TODO: notify user about role assignment
    }

    @Transactional
    override fun unassignRole(
        initiatorId: Long,
        targetId: Long,
        id: Long,
    ) {
        val role =
            roleEntityRepository.findById(id)
                ?: throw NotFoundException.RoleById(id)
        val communityId = role.communityId

        val community =
            communityEntityRepository.findById(communityId)
                ?: throw RuntimeException("Role id=$id exists but its related community id=$communityId does not.")

        val membership =
            membershipEntityRepository.findAllByUserId(initiatorId)
                .find { it.communityId == communityId }
                ?: if (community.isPublic) {
                    throw OperationDeniedException("Only members can unassign roles.")
                } else {
                    throw NotFoundException.CommunityById(communityId)
                }

        if (!hasRight(membership.id!!, roleUnassignPermission)) {
            throw PermissionDeniedException(initiatorId, roleUnassignPermission)
        }

        val targetMembership =
            membershipEntityRepository.findAllByUserId(targetId)
                .find { it.communityId == communityId }
                ?: if (community.isPublic) {
                    throw OperationDeniedException("Roles can be unassigned only from members.")
                } else {
                    throw NotFoundException.CommunityById(communityId)
                }

        roleEntityRepository.findAllByMembershipId(targetMembership.id!!)
            .find { it.id == role.id!! }
            ?: throw OperationDeniedException("Not assigned yet.")

        val ownPriority = getPriority(membership.id!!)
        if (!validatePriorityAccess(ownPriority, role.priority)) {
            throw OperationDeniedException("Unable to access priority.")
        }

        unassignRole(targetMembership, role)
    }

    override fun assignRole(
        membership: MembershipEntity,
        role: RoleEntity,
    ): MembershipRoleEntity =
        membershipRoleEntityRepository.save(
            MembershipRoleEntity(
                id = null,
                assignedAt = OffsetDateTime.now(),
                membershipId = membership.id!!,
                roleId = role.id!!,
            ),
        )

    @Transactional(propagation = Propagation.MANDATORY)
    override fun unassignRole(
        membership: MembershipEntity,
        role: RoleEntity,
    ) {
        val roleId = role.id!!
        val membershipId = membership.id!!

        val membershipRole =
            membershipRoleEntityRepository.findAllByMembershipId(membershipId)
                .find { it.roleId == roleId }
                ?: throw RuntimeException(
                    "Role id=$roleId not assigned to membership id=$membershipId" +
                        "(userId=${membership.userId})",
                )
        membershipRoleEntityRepository.remove(membershipRole)
    }

    override fun createRole(
        communityId: Long,
        roleName: String,
        priority: Int,
        protected: Boolean,
        permissionSet: RoleEntity.PermissionSet,
    ): RoleEntity =
        roleEntityRepository.save(
            RoleEntity(
                id = null,
                createdAt = OffsetDateTime.now(),
                updatedAt = OffsetDateTime.now(),
                communityId = communityId,
                name = roleName,
                priority = priority,
                protected = protected,
                permissionSet = permissionSet,
            ),
        )

    @Transactional(propagation = Propagation.MANDATORY)
    override fun deleteRole(role: RoleEntity) {
        membershipRoleEntityRepository.removeAllByRoleId(role.id!!)
        roleEntityRepository.remove(role)
    }

    override fun mapToPermissionSet(rights: Map<String, Boolean?>) =
        RoleEntity.PermissionSet(
            serverDelete = rights[CommunityPermission.SERVER_DELETE.toString()],
            serverEdit = rights[CommunityPermission.SERVER_EDIT.toString()],
            serverEditRoles = rights[CommunityPermission.SERVER_EDIT_ROLES.toString()],
            serverEditElements = rights[CommunityPermission.SERVER_EDIT_ELEMENTS.toString()],
            serverAssignRoles = rights[CommunityPermission.SERVER_ASSIGN_ROLES.toString()],
            serverCreateInvite = rights[CommunityPermission.SERVER_CREATE_INVITE.toString()],
            chatSendMessages = rights[CommunityPermission.CHAT_SEND_MESSAGES.toString()],
            voiceJoin = rights[CommunityPermission.VOICE_JOIN.toString()]
        )

    override fun getPriority(membershipId: Long): Int {
        val roleEntities = roleEntityRepository.findAllByMembershipId(membershipId)
        // Should be sorted by priority in desc order

        return roleEntities.firstOrNull()?.priority ?: defaultUserRolePriority
    }

    override fun hasRight(
        membershipId: Long,
        communityRight: CommunityPermission,
    ): Boolean {
        val roleEntities = roleEntityRepository.findAllByMembershipId(membershipId)
        // Should be sorted by priority in desc order

        roleEntities.forEach { role ->
            when (communityRight) {
                CommunityPermission.SERVER_DELETE -> role.permissionSet.serverDelete
                CommunityPermission.SERVER_EDIT -> role.permissionSet.serverEdit
                CommunityPermission.SERVER_EDIT_ROLES -> role.permissionSet.serverEditRoles
                CommunityPermission.SERVER_EDIT_ELEMENTS -> role.permissionSet.serverEditElements
                CommunityPermission.SERVER_ASSIGN_ROLES -> role.permissionSet.serverAssignRoles
                CommunityPermission.SERVER_CREATE_INVITE -> role.permissionSet.serverCreateInvite
                CommunityPermission.CHAT_SEND_MESSAGES -> role.permissionSet.chatSendMessages
                CommunityPermission.VOICE_JOIN -> role.permissionSet.voiceJoin
            }?.let {
                return it
            }
        }

        return false
    }

    override fun createDefaultUserRole(communityId: Long): RoleEntity =
        createRole(
            communityId,
            defaultUserRoleName,
            defaultUserRolePriority,
            true,
            defaultUserRolePermissionSet,
        )

    override fun createDefaultAdminRole(communityId: Long): RoleEntity =
        createRole(
            communityId,
            defaultAdminRoleName,
            defaultAdminRolePriority,
            true,
            defaultAdminRolePermissionSet,
        )

    override fun findDefaultUserRole(communityId: Long): RoleEntity? =
        roleEntityRepository.findAllByCommunityId(communityId)
            .find { it.protected && (it.name == defaultUserRoleName) }
}
