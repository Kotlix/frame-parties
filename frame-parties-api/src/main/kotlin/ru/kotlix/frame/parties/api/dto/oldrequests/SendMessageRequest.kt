package ru.kotlix.frame.parties.api.dto.oldrequests

data class SendMessageRequest(
    val communityId: Int,
    val chatId: Int,
    val message: String
)
