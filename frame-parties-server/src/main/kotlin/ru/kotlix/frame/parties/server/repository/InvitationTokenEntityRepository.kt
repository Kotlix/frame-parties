package ru.kotlix.frame.parties.server.repository

import ru.kotlix.frame.parties.server.repository.dto.InvitationTokenEntity

interface InvitationTokenEntityRepository {
    fun findById(id: Long): InvitationTokenEntity?

    fun save(entity: InvitationTokenEntity): InvitationTokenEntity

    fun findByToken(token: String): InvitationTokenEntity?
}