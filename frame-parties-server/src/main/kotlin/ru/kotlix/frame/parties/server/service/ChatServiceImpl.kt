package ru.kotlix.frame.parties.server.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.kotlix.frame.parties.server.exception.NotFoundException
import ru.kotlix.frame.parties.server.exception.OperationDeniedException
import ru.kotlix.frame.parties.server.exception.PermissionDeniedException
import ru.kotlix.frame.parties.server.repository.ChatEntityRepository
import ru.kotlix.frame.parties.server.repository.CommunityEntityRepository
import ru.kotlix.frame.parties.server.repository.MembershipEntityRepository
import ru.kotlix.frame.parties.server.repository.dto.ChatEntity
import ru.kotlix.frame.parties.server.service.dto.CommunityPermission
import java.time.OffsetDateTime

@Service
class ChatServiceImpl(
    private val chatRepository: ChatEntityRepository,
    private val roleService: RoleService,
    private val communityRepository: CommunityEntityRepository,
    private val membershipRepository: MembershipEntityRepository,
) : ChatService {
    private val chatUpdatePermission = CommunityPermission.SERVER_EDIT_ELEMENTS
    private val chatCreatePermission = CommunityPermission.SERVER_EDIT_ELEMENTS
    private val chatDeletePermission = CommunityPermission.SERVER_EDIT_ELEMENTS

    @Transactional
    override fun getAllChats(
        initiatorId: Long,
        communityId: Long,
    ): List<ChatEntity> {
        val communityEntity =
            communityRepository.findById(communityId)
                ?: throw NotFoundException.CommunityById(communityId)

        membershipRepository.findAllByUserId(initiatorId)
            .find { it.communityId == communityEntity.id }
            ?: if (communityEntity.isPublic) {
                throw OperationDeniedException("Only members can view community.")
            } else {
                throw NotFoundException.CommunityById(communityId)
            }

        return chatRepository.findAllByCommunityId(communityId)
    }

    @Transactional
    override fun getChatById(
        initiatorId: Long,
        id: Long,
    ): ChatEntity {
        val chat = chatRepository.findById(id)
            ?: throw NotFoundException.ChatById(id)

        val community =
            communityRepository.findById(chat.communityId)
                ?: throw RuntimeException("Chat id=$id exists but its related community id=${chat.communityId} does not.")

        val isMember =
            membershipRepository.findAllByUserId(initiatorId)
                .any { it.communityId == chat.communityId }
        if (!isMember) {
            if (community.isPublic) {
                throw OperationDeniedException("Only members can see community chats.")
            } else {
                throw NotFoundException.CommunityById(chat.communityId)
            }
        }

        return chat
    }

    @Transactional
    override fun createChat(
        initiatorId: Long,
        communityId: Long,
        name: String,
        directoryId: Long,
        order: Int,
    ): ChatEntity {
        val communityEntity =
            communityRepository.findById(communityId)
                ?: throw NotFoundException.CommunityById(communityId)

        val membershipEntity =
            membershipRepository.findAllByUserId(initiatorId)
                .find { it.communityId == communityEntity.id }
                ?: if (communityEntity.isPublic) {
                    throw OperationDeniedException("Only members can create chats.")
                } else {
                    throw NotFoundException.CommunityById(communityId)
                }

        if (!roleService.hasRight(membershipEntity.id!!, chatCreatePermission)) {
            throw PermissionDeniedException(initiatorId, chatCreatePermission)
        }

        return createChat(communityId, name, directoryId, order)
    }

    @Transactional
    override fun updateChat(
        initiatorId: Long,
        id: Long,
        name: String,
        directoryId: Long,
        order: Int,
    ): ChatEntity {
        val chat = chatRepository.findById(id) ?: throw NotFoundException.ChatById(id)

        val communityEntity =
            communityRepository.findById(chat.communityId)
                ?: throw NotFoundException.CommunityById(chat.communityId)

        val membershipEntity =
            membershipRepository.findAllByUserId(initiatorId)
                .find { it.communityId == communityEntity.id }
                ?: if (communityEntity.isPublic) {
                    throw OperationDeniedException("Only members can update chats.")
                } else {
                    throw NotFoundException.CommunityById(id)
                }

        if (!roleService.hasRight(membershipEntity.id!!, chatUpdatePermission)) {
            throw PermissionDeniedException(initiatorId, chatUpdatePermission)
        }

        chat.name = name
        chat.parentDirectoryId = directoryId
        chat.pos = order
        chat.updatedAt = OffsetDateTime.now()

        return chatRepository.update(chat)
    }

    @Transactional
    override fun deleteChat(
        initiatorId: Long,
        id: Long,
    ) {
        val chat = chatRepository.findById(id) ?: throw NotFoundException.ChatById(id)

        val communityEntity =
            communityRepository.findById(chat.communityId)
                ?: throw NotFoundException.CommunityById(chat.communityId)

        val membershipEntity =
            membershipRepository.findAllByUserId(initiatorId)
                .find { it.communityId == communityEntity.id }
                ?: if (communityEntity.isPublic) {
                    throw OperationDeniedException("Only members can delete chats.")
                } else {
                    throw NotFoundException.CommunityById(id)
                }

        if (!roleService.hasRight(membershipEntity.id!!, chatDeletePermission)) {
            throw PermissionDeniedException(initiatorId, chatDeletePermission)
        }

        chatRepository.delete(chat)

        // TODO: mark for messages for deleted
    }

    private fun createChat(
        communityId: Long,
        name: String,
        directoryId: Long,
        order: Int,
    ): ChatEntity {
        val now = OffsetDateTime.now()

        return chatRepository.save(
            ChatEntity(
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
