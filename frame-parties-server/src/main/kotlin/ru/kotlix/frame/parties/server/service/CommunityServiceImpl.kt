package ru.kotlix.frame.parties.server.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.kotlix.frame.parties.server.exception.NotFoundException
import ru.kotlix.frame.parties.server.exception.OperationDeniedException
import ru.kotlix.frame.parties.server.exception.PermissionDeniedException
import ru.kotlix.frame.parties.server.repository.CommunityEntityRepository
import ru.kotlix.frame.parties.server.repository.InvitationTokenEntityRepository
import ru.kotlix.frame.parties.server.repository.MembershipEntityRepository
import ru.kotlix.frame.parties.server.repository.MembershipRoleEntityRepository
import ru.kotlix.frame.parties.server.repository.dto.CommunityEntity
import ru.kotlix.frame.parties.server.repository.dto.InvitationTokenEntity
import ru.kotlix.frame.parties.server.repository.dto.MembershipEntity
import ru.kotlix.frame.parties.server.repository.dto.MembershipRoleEntity
import ru.kotlix.frame.parties.server.service.dto.CommunityPermission
import ru.kotlix.frame.voice.client.VoiceClient
import java.time.Duration
import java.time.OffsetDateTime

@Service
class CommunityServiceImpl(
    private val communityEntityRepository: CommunityEntityRepository,
    private val membershipEntityRepository: MembershipEntityRepository,
    private val membershipRoleEntityRepository: MembershipRoleEntityRepository,
    private val tokenEntityRepository: InvitationTokenEntityRepository,
    private val roleService: RoleService,
    private val tokenService: TokenService,
    private val directoryService: DirectoryService,
    private val chatService: ChatService,
    private val voiceClient: VoiceClient
) : CommunityService {
    private val communityCreationCooldown = Duration.ofMinutes(5)
    private val defaultRootDirectoryName = "root"
    private val defaultChatDirectoryName = "welcome"

    private val communityUpdatePermission = CommunityPermission.SERVER_EDIT
    private val communityDeletePermission = CommunityPermission.SERVER_DELETE
    private val communityCreateTokenPermission = CommunityPermission.SERVER_CREATE_INVITE

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

        val community = createCommunity(
            name,
            desc,
            isPublic,
            voiceName,
            voiceRegion,
            initiatorId
        )
        val membership = joinCommunity(community, initiatorId)
        val defaultUserRole = roleService.createDefaultUserRole(community.id!!)
        val defaultAdminRole = roleService.createDefaultAdminRole(community.id!!)
        roleService.assignRole(membership, defaultUserRole)
        roleService.assignRole(membership, defaultAdminRole)

        return community
    }

    private fun createCommunity(
        name: String,
        desc: String?,
        isPublic: Boolean,
        voiceName: String,
        voiceRegion: String,
        creatorId: Long
    ): CommunityEntity {
        val community =
            communityEntityRepository.save(
                CommunityEntity(
                    id = null,
                    createdAt = OffsetDateTime.now(),
                    updatedAt = OffsetDateTime.now(),
                    name = name,
                    description = desc,
                    isPublic = isPublic,
                    voiceName = voiceName,
                    voiceRegion = voiceRegion,
                    creatorId = creatorId,
                    deleted = false,
                ),
            )

        val directory =
            directoryService.createDirectory(
                community.id!!,
                defaultRootDirectoryName,
                null,
                0
            )

        chatService.createChat(
            community.id!!,
            defaultChatDirectoryName,
            directory.id!!,
            0
        )

        // TODO: create voice
        return community
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
                    throw OperationDeniedException("Only members can update community.")
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
                    throw OperationDeniedException("Only members can delete community.")
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

    override fun findAllByUserId(userId: Long): List<CommunityEntity> =
        communityEntityRepository.findAllByUserId(userId)

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
                throw OperationDeniedException("Only members can see members.")
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
            roleService.findDefaultUserRole(communityId)
                ?: throw RuntimeException("Community id=$communityId does not contain default user role.")

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

    @Transactional
    override fun joinCommunityByInviteToken(
        initiatorId: Long,
        token: String,
    ) {
        val tokenEntity =
            tokenEntityRepository.findByToken(token)
                ?: throw NotFoundException.Token(token)
        if (Duration.between(OffsetDateTime.now(), tokenEntity.expiresAt).isNegative) {
            throw OperationDeniedException("Token expired.")
        }
        if (tokenEntity.isOneTime && tokenEntity.useCount != 0) {
            throw OperationDeniedException("Token already used.")
        }
        val communityId = tokenEntity.communityId

        val community =
            communityEntityRepository.findById(communityId)
                ?: throw RuntimeException("Token found but targeting community id=$communityId not found.")

        membershipEntityRepository.findAllByUserId(initiatorId)
            .find { it.communityId == community.id }
            ?.let { throw OperationDeniedException("Attempt to rejoin community.") }

        val defaultUserRole =
            roleService.findDefaultUserRole(communityId)
                ?: throw RuntimeException("Community id=$communityId does not contain default user role.")

        val membership = joinCommunity(community, initiatorId)
        roleService.assignRole(membership, defaultUserRole)

        tokenEntityRepository.incrementUseCount(tokenEntity)

        // TODO: notify users about leave
    }

    @Transactional
    override fun createInviteToken(
        initiatorId: Long,
        communityId: Long,
        expiresAt: OffsetDateTime?,
        isOneTime: Boolean,
    ): InvitationTokenEntity {
        val communityEntity =
            communityEntityRepository.findById(communityId)
                ?: throw NotFoundException.CommunityById(communityId)

        val membershipEntity =
            membershipEntityRepository.findAllByUserId(initiatorId)
                .find { it.communityId == communityEntity.id }
                ?: if (communityEntity.isPublic) {
                    throw OperationDeniedException("Only members can create invitation tokens.")
                } else {
                    throw NotFoundException.CommunityById(communityId)
                }

        if (!roleService.hasRight(membershipEntity.id!!, communityCreateTokenPermission)) {
            throw PermissionDeniedException(initiatorId, communityCreateTokenPermission)
        }

        val token = tokenService.generateToken()

        val invitationTokenEntity =
            tokenEntityRepository.save(
                InvitationTokenEntity(
                    id = null,
                    createdAt = OffsetDateTime.now(),
                    createdBy = initiatorId,
                    token = token,
                    communityId = communityEntity.id!!,
                    isOneTime = isOneTime,
                    useCount = 0,
                    expiresAt = expiresAt,
                ),
            )
        return invitationTokenEntity
    }

    private fun joinCommunity(
        communityEntity: CommunityEntity,
        userId: Long,
    ): MembershipEntity =
        membershipEntityRepository.save(
            MembershipEntity(
                id = null,
                joinedAt = OffsetDateTime.now(),
                userId = userId,
                communityId = communityEntity.id!!,
            ),
        )
}
