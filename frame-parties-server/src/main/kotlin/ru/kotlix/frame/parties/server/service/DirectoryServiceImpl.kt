package ru.kotlix.frame.parties.server.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.kotlix.frame.parties.server.exception.DictionaryCreationException
import ru.kotlix.frame.parties.server.exception.NotFoundException
import ru.kotlix.frame.parties.server.exception.OperationDeniedException
import ru.kotlix.frame.parties.server.exception.PermissionDeniedException
import ru.kotlix.frame.parties.server.repository.CommunityEntityRepository
import ru.kotlix.frame.parties.server.repository.DirectoryEntityRepository
import ru.kotlix.frame.parties.server.repository.MembershipEntityRepository
import ru.kotlix.frame.parties.server.repository.dto.DirectoryEntity
import ru.kotlix.frame.parties.server.service.dto.CommunityPermission
import java.time.OffsetDateTime

@Service
class DirectoryServiceImpl(
    val directoryRepository: DirectoryEntityRepository,
    val communityEntityRepository: CommunityEntityRepository,
    val membershipEntityRepository: MembershipEntityRepository,
    private val roleService: RoleService
    ) : DirectoryService {

    private val directoryUpdatePermission = CommunityPermission.SERVER_EDIT_ELEMENTS
    private val directoryCreatePermission = CommunityPermission.SERVER_EDIT_ELEMENTS
    private val directoryDeletePermission = CommunityPermission.SERVER_EDIT_ELEMENTS

    @Transactional
    override fun getAllDirectories(initiatorId: Long, communityId: Long): List<DirectoryEntity> {
        val directories = directoryRepository.findAllByCommunityId(communityId)

        val isMember =
            membershipEntityRepository
                .findAllByUserId(initiatorId)
                .any { it.communityId == communityId }
        if (isMember) {
            return directories
        }
        throw NotFoundException.CommunityById(communityId)
    }

    @Transactional
    override fun getDirectoryById(initiatorId: Long, directoryId: Long): DirectoryEntity {
        return directoryRepository.findById(directoryId)
            ?: throw NotFoundException.DirectoryById(directoryId)
    }

    @Transactional
    override fun createDirectory(
        initiatorId: Long,
        communityId: Long,
        name: String,
        parentDirectoryId: Long?,
        order: Int
    ): DirectoryEntity {
        val communityEntity =
            communityEntityRepository.findById(communityId)
                ?: throw NotFoundException.CommunityById(communityId)

        val membershipEntity =
            membershipEntityRepository.findAllByUserId(initiatorId)
                .find { it.communityId == communityEntity.id }
                ?: if (communityEntity.isPublic) {
                    throw OperationDeniedException("Only members can create directory in this community.")
                } else {
                    throw NotFoundException.CommunityById(communityId)
                }

        if (!roleService.hasRight(membershipEntity.id!!, directoryCreatePermission)) {
            throw PermissionDeniedException(initiatorId, directoryCreatePermission)
        }

        if (parentDirectoryId == null) {
            throw DictionaryCreationException("Directory parent can not be null.")
        }

        return createDirectory(communityId, name, parentDirectoryId, order)
    }

    @Transactional
    override fun updateDirectory(
        initiatorId: Long,
        id: Long,
        name: String,
        directoryId: Long?,
        order: Int
    ): DirectoryEntity {
        val directory = directoryRepository.findById(id) ?: throw NotFoundException.DirectoryById(id)

        val communityEntity = communityEntityRepository.findById(directory.communityId)
            ?: throw NotFoundException.CommunityById(directory.communityId)

        val membershipEntity =
            membershipEntityRepository.findAllByUserId(initiatorId)
                .find { it.communityId == directory.communityId }
                ?: if (communityEntity.isPublic) {
                    throw OperationDeniedException("Only members can update directory.")
                } else {
                    throw NotFoundException.CommunityById(directory.communityId)
                }

        if (!roleService.hasRight(membershipEntity.id!!, directoryUpdatePermission)) {
            throw PermissionDeniedException(initiatorId, directoryUpdatePermission)
        }

        directory.name = name
        directory.parentDirectoryId =
            directoryId ?: throw DictionaryCreationException("Directory parent can not be null.")
        directory.pos = order
        directory.updatedAt = OffsetDateTime.now()
        return directoryRepository.update(directory)
    }

    @Transactional
    override fun deleteDirectory(initiatorId: Long, id: Long) {
        val directory = directoryRepository.findById(id) ?: throw NotFoundException.DirectoryById(id)

        val communityEntity = communityEntityRepository.findById(directory.communityId)
            ?: throw NotFoundException.CommunityById(directory.communityId)

        val membershipEntity =
            membershipEntityRepository.findAllByUserId(initiatorId)
                .find { it.communityId == directory.communityId }
                ?: if (communityEntity.isPublic) {
                    throw OperationDeniedException("Only members can delete directory.")
                } else {
                    throw NotFoundException.CommunityById(directory.communityId)
                }

        if (!roleService.hasRight(membershipEntity.id!!, directoryDeletePermission)) {
            throw PermissionDeniedException(initiatorId, directoryDeletePermission)
        }

        // TODO delete directory
    }

    fun createDirectory(communityId: Long, name: String, parentDirectoryId: Long?, order: Int): DirectoryEntity {
        val directory = DirectoryEntity(
            id = null,
            createdAt = OffsetDateTime.now(),
            updatedAt = OffsetDateTime.now(),
            communityId = communityId,
            name = name,
            parentDirectoryId = parentDirectoryId,
            pos = order
        )

        return directoryRepository.save(directory)
    }
}