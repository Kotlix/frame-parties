package ru.kotlix.frame.parties.server.service

import feign.FeignException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import ru.kotlix.frame.parties.api.dto.entities.ConnectionGuide
import ru.kotlix.frame.parties.server.exception.NotFoundException
import ru.kotlix.frame.parties.server.exception.OperationDeniedException
import ru.kotlix.frame.parties.server.exception.PermissionDeniedException
import ru.kotlix.frame.parties.server.mapper.toPartiesConnectionGuideDto
import ru.kotlix.frame.parties.server.repository.CommunityEntityRepository
import ru.kotlix.frame.parties.server.repository.MembershipEntityRepository
import ru.kotlix.frame.parties.server.repository.VoiceEntityRepository
import ru.kotlix.frame.parties.server.repository.dto.VoiceEntity
import ru.kotlix.frame.parties.server.service.dto.CommunityPermission
import ru.kotlix.frame.voice.api.dto.JoinRequest
import ru.kotlix.frame.voice.api.dto.LeaveRequest
import ru.kotlix.frame.voice.client.VoiceClient
import java.time.OffsetDateTime

@Service
class VoiceServiceImpl(
    val communityEntityRepository: CommunityEntityRepository,
    val membershipEntityRepository: MembershipEntityRepository,
    val voiceEntityRepository: VoiceEntityRepository,
    val roleService: RoleService,
    val voiceClient: VoiceClient,
) : VoiceService {
    private val voiceCreatePermission = CommunityPermission.SERVER_EDIT_ELEMENTS
    private val voiceUpdatePermission = CommunityPermission.SERVER_EDIT_ELEMENTS
    private val voiceDeletePermission = CommunityPermission.SERVER_EDIT_ELEMENTS
    private val voiceJoinPermission = CommunityPermission.VOICE_JOIN

    override fun getAllVoices(
        initiatorId: Long,
        communityId: Long,
    ): List<VoiceEntity> {
        val community =
            communityEntityRepository.findById(communityId)
                ?: throw NotFoundException.CommunityById(communityId)

        val voices = voiceEntityRepository.findAllByCommunityId(community.id!!)

        val isMember =
            membershipEntityRepository.findAllByUserId(initiatorId)
                .any { it.communityId == communityId }
        if (!isMember) {
            if (community.isPublic) {
                throw OperationDeniedException("Only members can see community voices.")
            } else {
                throw NotFoundException.CommunityById(communityId)
            }
        }

        return voices
    }

    override fun getVoiceById(
        initiatorId: Long,
        id: Long,
    ): VoiceEntity {
        val voice = voiceEntityRepository.findById(id) ?: throw NotFoundException.VoiceById(id)

        val community =
            communityEntityRepository.findById(voice.communityId)
                ?: throw RuntimeException(
                    "Voice id=$voice exists but its" +
                        " related community id=${voice.communityId} does not.",
                )

        val isMember =
            membershipEntityRepository.findAllByUserId(initiatorId)
                .any { it.communityId == voice.communityId }
        if (!isMember) {
            if (community.isPublic) {
                throw OperationDeniedException("Only members can see community voices.")
            } else {
                throw NotFoundException.CommunityById(voice.communityId)
            }
        }

        return voice
    }

    override fun createVoice(
        initiatorId: Long,
        communityId: Long,
        name: String,
        directoryId: Long,
        order: Int,
    ): VoiceEntity {
        val communityEntity =
            communityEntityRepository.findById(communityId)
                ?: throw NotFoundException.CommunityById(communityId)

        val membershipEntity =
            membershipEntityRepository.findAllByUserId(initiatorId)
                .find { it.communityId == communityEntity.id }
                ?: if (communityEntity.isPublic) {
                    throw OperationDeniedException("Only members can create voice in this community.")
                } else {
                    throw NotFoundException.CommunityById(communityId)
                }

        if (!roleService.hasRight(membershipEntity.id!!, voiceCreatePermission)) {
            throw PermissionDeniedException(initiatorId, voiceCreatePermission)
        }

        return createVoice(communityId, name, directoryId, order)
    }

    override fun updateVoice(
        initiatorId: Long,
        id: Long,
        name: String,
        directoryId: Long,
        order: Int,
    ): VoiceEntity {
        val voice =
            voiceEntityRepository.findById(id)
                ?: throw NotFoundException.VoiceById(id)

        val communityEntity =
            communityEntityRepository.findById(voice.communityId)
                ?: throw RuntimeException(
                    "Voice id=$voice exists but its" +
                        " related community id=${voice.communityId} does not.",
                )

        val membershipEntity =
            membershipEntityRepository.findAllByUserId(initiatorId)
                .find { it.communityId == voice.communityId }
                ?: if (communityEntity.isPublic) {
                    throw OperationDeniedException("Only members can update voice.")
                } else {
                    throw NotFoundException.CommunityById(voice.communityId)
                }

        if (!roleService.hasRight(membershipEntity.id!!, voiceUpdatePermission)) {
            throw PermissionDeniedException(initiatorId, voiceUpdatePermission)
        }

        voice.updatedAt = OffsetDateTime.now()
        voice.name = name
        voice.parentDirectoryId = directoryId
        voice.pos = order

        return voiceEntityRepository.update(voice)
    }

    override fun deleteVoice(
        initiatorId: Long,
        id: Long,
    ) {
        val voice =
            voiceEntityRepository.findById(id)
                ?: throw NotFoundException.VoiceById(id)

        val communityEntity =
            communityEntityRepository.findById(voice.communityId)
                ?: throw RuntimeException(
                    "Voice id=$voice exists but its" +
                        " related community id=${voice.communityId} does not.",
                )

        val membershipEntity =
            membershipEntityRepository.findAllByUserId(initiatorId)
                .find { it.communityId == voice.communityId }
                ?: if (communityEntity.isPublic) {
                    throw OperationDeniedException("Only members can delete voice.")
                } else {
                    throw NotFoundException.CommunityById(voice.communityId)
                }

        if (!roleService.hasRight(membershipEntity.id!!, voiceDeletePermission)) {
            throw PermissionDeniedException(initiatorId, voiceDeletePermission)
        }

        if (voiceClient.getUsers(voice.id!!).size > 1) {
            throw OperationDeniedException("Cannot delete voice with other users.")
        }

        // TODO delete voice
    }

    override fun joinVoice(
        initiatorId: Long,
        id: Long,
    ): ConnectionGuide {
        val voice =
            voiceEntityRepository.findById(id)
                ?: throw NotFoundException.VoiceById(id)

        val communityEntity =
            communityEntityRepository.findById(voice.communityId)
                ?: throw RuntimeException(
                    "Voice id=$voice exists but its" +
                        " related community id=${voice.communityId} does not.",
                )

        val member =
            membershipEntityRepository.findAllByUserId(initiatorId)
                .find { it.communityId == voice.communityId }
                ?: if (communityEntity.isPublic) {
                    throw OperationDeniedException("Only members can join voice.")
                } else {
                    throw NotFoundException.CommunityById(voice.communityId)
                }

        if (!roleService.hasRight(member.id!!, voiceJoinPermission)) {
            throw PermissionDeniedException(initiatorId, voiceJoinPermission)
        }

        try {
            return voiceClient.joinChannel(
                JoinRequest(
                    userId = initiatorId,
                    voiceId = id,
                    serverName = communityEntity.voiceName,
                    serverRegion = communityEntity.voiceRegion,
                ),
            ).toPartiesConnectionGuideDto()
        } catch (ex: FeignException) {
            throw ResponseStatusException(
                HttpStatus.valueOf(ex.status()),
                ex.contentUTF8() ?: ex.message,
            )
        }
    }

    override fun leaveVoice(
        initiatorId: Long,
        id: Long,
    ) {
        voiceEntityRepository.findById(id)
            ?: throw NotFoundException.VoiceById(id)

        try {
            voiceClient.leaveChannel(
                LeaveRequest(
                    userId = initiatorId,
                ),
            )
        } catch (ex: FeignException) {
            throw ResponseStatusException(
                HttpStatus.valueOf(ex.status()),
                ex.contentUTF8() ?: ex.message,
            )
        }
    }

    override fun getVoiceUsers(
        initiatorId: Long,
        id: Long,
    ): List<Long> {
        val voice =
            voiceEntityRepository.findById(id)
                ?: throw NotFoundException.VoiceById(id)

        try {
            return voiceClient.getUsers(voice.id!!)
        } catch (ex: FeignException) {
            throw ResponseStatusException(
                HttpStatus.valueOf(ex.status()),
                ex.contentUTF8() ?: ex.message,
            )
        }
    }

    override fun createVoice(
        communityId: Long,
        name: String,
        directoryId: Long,
        order: Int,
    ): VoiceEntity {
        val now = OffsetDateTime.now()

        return voiceEntityRepository.save(
            VoiceEntity(
                id = null,
                createdAt = now,
                updatedAt = now,
                communityId = communityId,
                name = name,
                parentDirectoryId = directoryId,
                pos = order,
            ),
        )
    }
}
