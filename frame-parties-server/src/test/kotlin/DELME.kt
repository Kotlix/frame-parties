import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.kotlix.frame.parties.server.repository.*
import ru.kotlix.frame.parties.server.repository.dto.*
import java.time.OffsetDateTime
import java.util.*

@Profile("dev")
@Component
class DELME(
    private val chatEntityRepository: ChatEntityRepository,
    private val communityEntityRepository: CommunityEntityRepository,
    private val directoryEntityRepository: DirectoryEntityRepository,
    private val invitationTokenEntityRepository: InvitationTokenEntityRepository,
    private val membershipEntityRepository: MembershipEntityRepository,
    private val membershipRoleEntityRepository: MembershipRoleEntityRepository,
    private val roleEntityRepository: RoleEntityRepository,
    private val textMessageEntityRepository: TextMessageEntityRepository,
    private val voiceEntityRepository: VoiceEntityRepository
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    private val random = Random()

    @EventListener
    fun onSetup(event: ContextRefreshedEvent) {
        logger.info("Random testing...")

        val communityEntity = communityEntityRepository.save(
            CommunityEntity(
                id = null,
                createdAt = OffsetDateTime.now(),
                updatedAt = OffsetDateTime.now(),
                name = UUID.randomUUID().toString(),
                isPublic = random.nextBoolean(),
                description = UUID.randomUUID().toString()
            )
        )
        communityEntityRepository.findById(communityEntity.id!!)!!
        communityEntityRepository.findAllPublic(0, 1)
        if (communityEntity.isPublic) {
            communityEntityRepository.findAllPublicByName(communityEntity.name, 0, 1).first()
        }

        val membershipEntity = membershipEntityRepository.save(
            MembershipEntity(
                id = null,
                joinedAt = OffsetDateTime.now(),
                userId = random.nextLong(),
                communityId = communityEntity.id!!
            )
        )
        membershipEntityRepository.findById(membershipEntity.id!!)!!
        membershipEntityRepository.findAllByUserId(membershipEntity.userId).first()

        val roleEntity = roleEntityRepository.save(
            RoleEntity(
                id = null,
                createdAt = OffsetDateTime.now(),
                updatedAt = OffsetDateTime.now(),
                communityId = communityEntity.id!!,
                name = UUID.randomUUID().toString(),
                priority = random.nextInt(),
                rights = RoleEntity.Rights(true, true, true, true, true, true, true)
            )
        )
        roleEntityRepository.findById(roleEntity.id!!)!!
        roleEntityRepository.findAllByCommunityId(roleEntity.communityId).first()

        val membershipRoleEntity = membershipRoleEntityRepository.save(
            MembershipRoleEntity(
                id = null,
                assignedAt = OffsetDateTime.now(),
                userId = random.nextLong(),
                communityId = communityEntity.id!!,
                roleId = roleEntity.id!!
            )
        )
        membershipRoleEntityRepository.findById(membershipRoleEntity.id!!)!!
        membershipRoleEntityRepository.findAllByUserIdAndCommunityId(
            membershipRoleEntity.userId,
            membershipRoleEntity.communityId
        )

        val invitationTokenEntity = invitationTokenEntityRepository.save(
            InvitationTokenEntity(
                id = null,
                createdAt = OffsetDateTime.now(),
                createdBy = random.nextLong(),
                token = UUID.randomUUID().toString(),
                communityId = communityEntity.id!!,
                isOneTime = random.nextBoolean(),
                expiresAt = OffsetDateTime.now()
            )
        )
        invitationTokenEntityRepository.findById(invitationTokenEntity.id!!)!!
        invitationTokenEntityRepository.findByToken(invitationTokenEntity.token)!!

        val directoryEntity = directoryEntityRepository.save(
            DirectoryEntity(
                id = null,
                createdAt = OffsetDateTime.now(),
                updatedAt = OffsetDateTime.now(),
                communityId = communityEntity.id!!,
                name = UUID.randomUUID().toString(),
                parentDirectoryId = null,
                pos = random.nextInt()
            )
        )
        directoryEntityRepository.findById(directoryEntity.id!!)!!
        directoryEntityRepository.findAllByCommunityId(directoryEntity.communityId).first()

        val chatEntity = chatEntityRepository.save(
            ChatEntity(
                id = null,
                createdAt = OffsetDateTime.now(),
                updatedAt = OffsetDateTime.now(),
                communityId = communityEntity.id!!,
                name = UUID.randomUUID().toString(),
                parentDirectoryId = directoryEntity.id!!,
                pos = random.nextInt()
            )
        )
        chatEntityRepository.findById(chatEntity.id!!)!!
        chatEntityRepository.findAllByCommunityId(chatEntity.communityId).first()

        val voiceEntity = voiceEntityRepository.save(
            VoiceEntity(
                id = null,
                createdAt = OffsetDateTime.now(),
                updatedAt = OffsetDateTime.now(),
                communityId = communityEntity.id!!,
                name = UUID.randomUUID().toString(),
                parentDirectoryId = directoryEntity.id!!,
                pos = random.nextInt()
            )
        )
        voiceEntityRepository.findById(voiceEntity.id!!)!!
        voiceEntityRepository.findAllByCommunityId(voiceEntity.communityId).first()

        val textMessageEntity = textMessageEntityRepository.save(
            TextMessageEntity(
                id = null,
                createdAt = OffsetDateTime.now(),
                chatId = chatEntity.id!!,
                userId = random.nextLong(),
                message = UUID.randomUUID().toString()
            )
        )
        textMessageEntityRepository.findById(textMessageEntity.id!!)!!
        textMessageEntityRepository.findAllByChatId(textMessageEntity.chatId).first()
    }
}