package ru.kotlix.frame.parties.api.dto

data class DeleteCommunityElementRequest(
    val communityId: String,
    val elementId: String
)
