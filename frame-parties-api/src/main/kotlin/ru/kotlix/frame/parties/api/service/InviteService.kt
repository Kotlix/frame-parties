package ru.kotlix.frame.parties.api.service

import ru.kotlix.frame.parties.api.dto.entities.InviteDto

interface InviteService {
    fun createInvite(dto: InviteDto): InviteDto
    fun getInvite(token: String): InviteDto
    fun revokeInvite(token: String): Unit
}
