package ru.kotlix.frame.parties.api.dto

import ru.kotlix.frame.parties.api.dto.entities.RoleDto

data class ChangeCommunityInfoRequest(
    val id: Int,
    val name: String,
    val desc: String,
    val communityId: Int,
    val members: List<Int>,
    val chats: List<Int>,
    val channels: List<Int>,
    val voiceChats: List<Int>,
    val roles: List<RoleDto>
)
