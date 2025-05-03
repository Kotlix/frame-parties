package ru.kotlix.frame.parties.server.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.kotlix.frame.parties.server.exception.NotFoundException
import ru.kotlix.frame.parties.server.exception.OperationDeniedException
import ru.kotlix.frame.parties.server.exception.PermissionDeniedException
import ru.kotlix.frame.parties.server.repository.CommunityEntityRepository
import ru.kotlix.frame.parties.server.repository.MembershipEntityRepository
import ru.kotlix.frame.parties.server.repository.MembershipRoleEntityRepository
import ru.kotlix.frame.parties.server.repository.RoleEntityRepository
import ru.kotlix.frame.parties.server.repository.dto.CommunityEntity
import ru.kotlix.frame.parties.server.repository.dto.InvitationTokenEntity
import ru.kotlix.frame.parties.server.repository.dto.MembershipEntity
import ru.kotlix.frame.parties.server.repository.dto.MembershipRoleEntity
import ru.kotlix.frame.parties.server.repository.dto.RoleEntity
import ru.kotlix.frame.parties.server.service.dto.CommunityPermission
import ru.kotlix.frame.voice.client.VoiceClient
import java.time.Duration
import java.time.OffsetDateTime

@Service
class CommunityServiceImpl(
    private val voiceClient: VoiceClient,
    private val roleService: RoleService,
    private val communityEntityRepository: CommunityEntityRepository,
    private val membershipEntityRepository: MembershipEntityRepository,
    private val roleEntityRepository: RoleEntityRepository,
    private val membershipRoleEntityRepository: MembershipRoleEntityRepository,
    private val tokenService: TokenService,
) : CommunityService {
    companion object {
        val communityCreationCooldown = Duration.ofMinutes(5)

        val defaultUserRoleName = "default"
        val defaultUserRoleRights =
            RoleEntity.Rights(
                serverDelete = false,
                serverEdit = false,
                serverEditRoles = false,
                serverEditElements = false,
                serverAssignRoles = false,
                serverCreateInvite = false,
                chatSendMessages = true,
                voiceJoin = true,
            )

        val defaultAdminRoleName = "admin"
        val defaultAdminRoleRights =
            RoleEntity.Rights(
                serverDelete = true,
                serverEdit = true,
                serverEditRoles = true,
                serverEditElements = true,
                serverAssignRoles = true,
                serverCreateInvite = true,
                chatSendMessages = true,
                voiceJoin = true,
            )

        val communityUpdatePermission = CommunityPermission.SERVER_EDIT
        val communityDeletePermission = CommunityPermission.SERVER_DELETE
    }

    @Transactional
    override fun getById(
        initiatorId: Long,
        communityId: Long,
    ): CommunityEntity {
        val community =
            communityEntityRepository.findById(communityId)
                ?: throw NotFoundException.CommunityById(communityId)

        if (community.isPublic) {
            return community
        }
        val isMember =
            membershipEntityRepository
                .findAllByUserId(initiatorId)
                .any { it.communityId == communityId }
        if (isMember) {
            return community
        }
        throw NotFoundException.CommunityById(communityId)
    }

    @Transactional
    override fun createCommunity(
        initiatorId: Long,
        name: String,
        desc: String?,
        isPublic: Boolean,
        voiceRegion: String,
        voiceName: String,
    ): CommunityEntity {
        communityEntityRepository.findAllByCreatorId(initiatorId)
            .lastOrNull()?.let { Duration.between(OffsetDateTime.now(), it.createdAt.plus(communityCreationCooldown)) }
            ?.takeUnless { it.isNegative }?.let {
                throw OperationDeniedException("Too frequent attempt! Await $it")
            }

        voiceClient.getServers()[voiceRegion]?.first { it == voiceName }
            ?: throw NotFoundException.ServerByRegionAndName(voiceRegion, voiceName)

        val now = OffsetDateTime.now()

        val communityEntity =
            communityEntityRepository.save(
                CommunityEntity(
                    id = null,
                    createdAt = now,
                    updatedAt = now,
                    name = name,
                    description = desc,
                    isPublic = isPublic,
                    voiceName = voiceName,
                    voiceRegion = voiceRegion,
                    creatorId = initiatorId,
                    deleted = false,
                ),
            )
        val userRoleEntity =
            roleEntityRepository.save(
                RoleEntity(
                    id = null,
                    createdAt = now,
                    updatedAt = now,
                    communityId = communityEntity.id!!,
                    name = defaultUserRoleName,
                    priority = 1,
                    protected = true,
                    rights = defaultUserRoleRights,
                ),
            )
        val adminRoleEntity =
            roleEntityRepository.save(
                RoleEntity(
                    id = null,
                    createdAt = now,
                    updatedAt = now,
                    communityId = communityEntity.id!!,
                    name = defaultAdminRoleName,
                    priority = 100,
                    protected = true,
                    rights = defaultAdminRoleRights,
                ),
            )
        val membershipEntity =
            membershipEntityRepository.save(
                MembershipEntity(
                    id = null,
                    joinedAt = now,
                    userId = initiatorId,
                    communityId = communityEntity.id!!,
                ),
            )
        membershipRoleEntityRepository.save(
            MembershipRoleEntity(
                id = null,
                assignedAt = now,
                membershipId = membershipEntity.id!!,
                roleId = userRoleEntity.id!!,
            ),
        )
        membershipRoleEntityRepository.save(
            MembershipRoleEntity(
                id = null,
                assignedAt = now,
                membershipId = membershipEntity.id!!,
                roleId = adminRoleEntity.id!!,
            ),
        )

        return communityEntity
    }

    @Transactional
    override fun update(
        initiatorId: Long,
        communityId: Long,
        name: String,
        desc: String?,
        isPublic: Boolean,
        voiceRegion: String,
        voiceName: String,
    ): CommunityEntity {
        val communityEntity =
            communityEntityRepository.findById(communityId)
                ?: throw NotFoundException.CommunityById(communityId)

        val membershipEntity =
            membershipEntityRepository.findAllByUserId(initiatorId)
                .find { it.communityId == communityEntity.id }
                ?: if (communityEntity.isPublic) {
                    throw PermissionDeniedException(initiatorId, communityUpdatePermission)
                } else {
                    throw NotFoundException.CommunityById(communityId)
                }

        if (!roleService.hasRight(membershipEntity.id!!, communityUpdatePermission)) {
            throw PermissionDeniedException(initiatorId, communityUpdatePermission)
        }

        communityEntity.updatedAt = OffsetDateTime.now()
        communityEntity.name = name
        communityEntity.description = desc
        communityEntity.isPublic = isPublic
        communityEntity.voiceRegion = voiceRegion
        communityEntity.voiceName = voiceName
        return communityEntityRepository.update(communityEntity)
    }

    @Transactional
    override fun delete(
        initiatorId: Long,
        communityId: Long,
    ) {
        val communityEntity =
            communityEntityRepository.findById(communityId)
                ?: throw NotFoundException.CommunityById(communityId)

        val membershipEntity =
            membershipEntityRepository.findAllByUserId(initiatorId)
                .find { it.communityId == communityEntity.id }
                ?: if (communityEntity.isPublic) {
                    throw PermissionDeniedException(initiatorId, communityDeletePermission)
                } else {
                    throw NotFoundException.CommunityById(communityId)
                }

        if (!roleService.hasRight(membershipEntity.id!!, communityDeletePermission)) {
            throw PermissionDeniedException(initiatorId, communityDeletePermission)
        }

        communityEntity.deleted = true
        communityEntityRepository.update(communityEntity)

        // TODO: notify users about deletion
    }

    override fun findAllPublicWithFilter(
        initiatorId: Long,
        name: String?,
        pageOffset: Long,
        pageSize: Long,
    ): List<CommunityEntity> =
        name?.let { communityEntityRepository.findAllPublicByName(name, pageOffset, pageSize) }
            ?: communityEntityRepository.findAllPublic(pageOffset, pageSize)

    override fun findAllByUserId(userId: Long): List<CommunityEntity> = communityEntityRepository.findAllByUserId(userId)

    @Transactional
    override fun getMembers(
        initiatorId: Long,
        communityId: Long,
    ): List<MembershipEntity> {
        val communityEntity =
            communityEntityRepository.findById(communityId)
                ?: throw NotFoundException.CommunityById(communityId)

        membershipEntityRepository.findAllByUserId(initiatorId)
            .find { it.communityId == communityEntity.id }
            ?: if (communityEntity.isPublic) {
                throw PermissionDeniedException("Only members can see members.")
            } else {
                throw NotFoundException.CommunityById(communityId)
            }

        return membershipEntityRepository.findAllByCommunityId(communityId)
    }

    @Transactional
    override fun joinCommunity(
        initiatorId: Long,
        communityId: Long,
    ) {
        val communityEntity =
            communityEntityRepository.findById(communityId)
                ?: throw NotFoundException.CommunityById(communityId)

        membershipEntityRepository.findAllByUserId(initiatorId)
            .find { it.communityId == communityEntity.id }?.let {
                throw OperationDeniedException("Attempt to rejoin community.")
            }
        if (!communityEntity.isPublic) {
            throw NotFoundException.CommunityById(communityId)
        }

        val now = OffsetDateTime.now()
        val userRole =
            roleEntityRepository.findAllByCommunityId(communityId).find { it.name == defaultUserRoleName }
                ?: throw RuntimeException(
                    "Community id=$communityId does not contain " +
                        "protected default user role named '$defaultUserRoleName'.",
                )

        val membershipEntity =
            membershipEntityRepository.save(
                MembershipEntity(
                    id = null,
                    joinedAt = now,
                    userId = initiatorId,
                    communityId = communityEntity.id!!,
                ),
            )
        membershipRoleEntityRepository.save(
            MembershipRoleEntity(
                id = null,
                assignedAt = now,
                membershipId = membershipEntity.id!!,
                roleId = userRole.id!!,
            ),
        )
    }

    override fun joinCommunityByInviteToken(
        initiatorId: Long,
        token: String,
    ) = tokenService.useToken(initiatorId, token)

    @Transactional
    override fun leaveCommunity(
        initiatorId: Long,
        communityId: Long,
    ) {
        val communityEntity =
            communityEntityRepository.findById(communityId)
                ?: throw NotFoundException.CommunityById(communityId)

        if (communityEntity.creatorId == initiatorId) {
            throw OperationDeniedException("Creator can not leave own community.")
        }

        val membershipEntity =
            membershipEntityRepository.findAllByUserId(initiatorId)
                .find { it.communityId == communityEntity.id }
                ?: if (communityEntity.isPublic) {
                    throw OperationDeniedException("Attempt to leave community as non attendant.")
                } else {
                    throw NotFoundException.CommunityById(communityId)
                }

        membershipRoleEntityRepository.removeAllByMembershipId(membershipEntity.id!!)
        membershipEntityRepository.remove(membershipEntity)

        // TODO: notify users about leave
    }

    override fun createInviteToken(
        initiatorId: Long,
        communityId: Long,
        expiresAt: OffsetDateTime?,
        isOneTime: Boolean,
    ): InvitationTokenEntity = tokenService.generateToken(initiatorId, communityId, isOneTime, expiresAt)
}
