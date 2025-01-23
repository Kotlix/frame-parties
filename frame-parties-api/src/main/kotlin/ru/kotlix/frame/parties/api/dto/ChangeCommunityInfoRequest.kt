package ru.kotlix.frame.parties.api.dto

import ru.kotlix.frame.parties.api.dto.entities.Role

data class ChangeCommunityInfoRequest(
    val id: String,
    val name: String,
    val desc: String,
    val communityId: String,
    val members: List<String>,
    val chats: List<String>,
    val channels: List<String>,
    val voiceChats: List<String>,
    val roles: List<Role>
)
