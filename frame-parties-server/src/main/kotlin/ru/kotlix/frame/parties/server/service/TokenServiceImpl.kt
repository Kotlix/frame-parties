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
import ru.kotlix.frame.parties.server.repository.RoleEntityRepository
import ru.kotlix.frame.parties.server.repository.dto.InvitationTokenEntity
import ru.kotlix.frame.parties.server.repository.dto.MembershipEntity
import ru.kotlix.frame.parties.server.repository.dto.MembershipRoleEntity
import ru.kotlix.frame.parties.server.service.dto.CommunityPermission
import java.security.SecureRandom
import java.time.Duration
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.Base64

@Service
class TokenServiceImpl(
    private val roleService: RoleService,
    private val tokenEntityRepository: InvitationTokenEntityRepository,
    private val communityEntityRepository: CommunityEntityRepository,
    private val membershipEntityRepository: MembershipEntityRepository,
    private val roleEntityRepository: RoleEntityRepository,
    private val membershipRoleEntityRepository: MembershipRoleEntityRepository,
) : TokenService {
    private val secureRandom = SecureRandom(LocalDateTime.now().toString().toByteArray())

    private val generateTokenPermission = CommunityPermission.SERVER_CREATE_INVITE

    @Transactional
    override fun generateToken(
        initiatorId: Long,
        communityId: Long,
        isOneTime: Boolean,
        expiresAt: OffsetDateTime?,
    ): InvitationTokenEntity {
        val communityEntity =
            communityEntityRepository.findById(communityId)
                ?: throw NotFoundException.CommunityById(communityId)

        val membershipEntity =
            membershipEntityRepository.findAllByUserId(initiatorId)
                .find { it.communityId == communityEntity.id }
                ?: if (communityEntity.isPublic) {
                    throw PermissionDeniedException(initiatorId, generateTokenPermission)
                } else {
                    throw NotFoundException.CommunityById(communityId)
                }

        if (!roleService.hasRight(membershipEntity.id!!, generateTokenPermission)) {
            throw PermissionDeniedException(initiatorId, generateTokenPermission)
        }

        var token: String
        do {
            token = randomTokenGeneration()
        } while (tokenEntityRepository.findByToken(token) != null)

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

    override fun useToken(
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

        val communityEntity =
            communityEntityRepository.findById(communityId)
                ?: throw RuntimeException("Token found but targeting community id=$communityId not found.")

        membershipEntityRepository.findAllByUserId(initiatorId)
            .find { it.communityId == communityEntity.id }
            ?.let {
                throw OperationDeniedException("Attempt to rejoin community.")
            }

        val defaultUserRoleName = CommunityServiceImpl.defaultUserRoleName
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
        tokenEntityRepository.incrementUseCount(tokenEntity)
    }

    private fun randomTokenGeneration(): String =
        ByteArray(32).let {
            secureRandom.nextBytes(it)
            Base64.getUrlEncoder().withoutPadding().encodeToString(it)
        }
}
