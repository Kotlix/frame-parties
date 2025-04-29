package ru.kotlix.frame.parties.api.dto.oldrequests

data class GetMessagesRequest(
    val communityId: Int,
    val chatId: Int,
    val from: Int,
    val limit: Int
)
