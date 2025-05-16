package ru.kotlix.frame.parties.server.service

import org.springframework.stereotype.Service
import ru.kotlix.frame.parties.server.exception.NotFoundException
import ru.kotlix.frame.parties.server.exception.OperationDeniedException
import ru.kotlix.frame.parties.server.exception.PermissionDeniedException
import ru.kotlix.frame.parties.server.producer.MessageProducer
import ru.kotlix.frame.parties.server.repository.ChatEntityRepository
import ru.kotlix.frame.parties.server.repository.CommunityEntityRepository
import ru.kotlix.frame.parties.server.repository.MembershipEntityRepository
import ru.kotlix.frame.parties.server.repository.TextMessageEntityRepository
import ru.kotlix.frame.parties.server.repository.dto.TextMessageEntity
import ru.kotlix.frame.parties.server.service.dto.CommunityPermission
import ru.kotlix.frame.session.api.kafka.MessageNotification
import java.time.OffsetDateTime

@Service
class MessageServiceImpl(
    private val messageRepository: TextMessageEntityRepository,
    private val chatRepository: ChatEntityRepository,
    private val communityEntityRepository: CommunityEntityRepository,
    private val membershipRepository: MembershipEntityRepository,
    private val roleService: RoleService,
    private val messageProducer: MessageProducer,
) : MessageService {
    private val chatSendMessagePermission = CommunityPermission.CHAT_SEND_MESSAGES

    override fun sendMessage(
        initiatorId: Long,
        chatId: Long,
        message: String,
    ): TextMessageEntity {
        val chat =
            chatRepository.findById(chatId)
                ?: throw NotFoundException.ChatById(chatId)

        val community =
            communityEntityRepository.findById(chat.communityId)
                ?: throw RuntimeException("Chat id=$chatId exists but its related community id=${chat.communityId} does not.")

        val membershipEntity =
            membershipRepository.findAllByUserId(initiatorId)
                .find { it.communityId == community.id }
                ?: if (community.isPublic) {
                    throw OperationDeniedException("Only members can send messages in this community.")
                } else {
                    throw NotFoundException.CommunityById(chat.communityId)
                }

        if (!roleService.hasRight(membershipEntity.id!!, chatSendMessagePermission)) {
            throw PermissionDeniedException(initiatorId, chatSendMessagePermission)
        }

        val messageEntity =
            messageRepository.save(
                TextMessageEntity(
                    id = null,
                    createdAt = OffsetDateTime.now(),
                    chatId = chatId,
                    userId = initiatorId,
                    message = message,
                ),
            )

        messageProducer.produceMessage(
            MessageNotification().apply {
                communityId = community.id!!
                setChatId(messageEntity.chatId)
                senderId = messageEntity.userId
                textContent = messageEntity.message
            },
        )

        return messageEntity
    }

    override fun getMessages(
        initiatorId: Long,
        chatId: Long,
        page: Long,
        size: Long,
    ): List<TextMessageEntity> {
        val chat =
            chatRepository.findById(chatId)
                ?: throw NotFoundException.ChatById(chatId)

        val community =
            communityEntityRepository.findById(chat.communityId)
                ?: throw RuntimeException("Chat id=$chatId exists but its related community id=${chat.communityId} does not.")

        membershipRepository.findAllByUserId(initiatorId)
            .find { it.communityId == community.id }
            ?: if (community.isPublic) {
                throw OperationDeniedException("Only members can get messages in this community.")
            } else {
                throw NotFoundException.CommunityById(chat.communityId)
            }

        return messageRepository.findAllByChatId(chatId, page, size)
    }

    override fun getMessageById(
        initiatorId: Long,
        messageId: Long,
    ): TextMessageEntity {
        val message =
            messageRepository.findById(messageId)
                ?: throw NotFoundException.MessageById(messageId)

        val chat =
            chatRepository.findById(message.chatId)
                ?: throw NotFoundException.ChatById(message.chatId)

        val community =
            communityEntityRepository.findById(chat.communityId)
                ?: throw RuntimeException("Chat id=${message.chatId} exists but its related community id=${chat.communityId} does not.")

        membershipRepository.findAllByUserId(initiatorId)
            .find { it.communityId == community.id }
            ?: if (community.isPublic) {
                throw OperationDeniedException("Only members can get message in this community.")
            } else {
                throw NotFoundException.CommunityById(chat.communityId)
            }

        return message
    }
}
